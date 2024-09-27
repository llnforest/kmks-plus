package com.kmks.w2.service.impl.supervise;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.vo.A0221000008Vo;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.domain.W2Ksy;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.dto.SplitCarServerResponseDto;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2KsyVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.service.IW2LineconfigService;
import com.kmks.w2.service.impl.ImageServiceImpl;
import com.kmks.w2.utils.RedisUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.exception.api.SuccessException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: TODO
 * @date 2024/9/18 9:58
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonHandlerService {
    private final ISysConfigService configService;
    private final ImageServiceImpl imageService;
    private final W2RecordsMapper recordsMapper;
    private final W2QueuingMapper queuingMapper;
    private final W2KcxxMapper kcxxMapper;

    private final IW2LineconfigService lineconfigService;


    /**
     * 同步到本地时间
     *
     * @param dateTime 日期时间
     * @param date     日期
     * @param time     时间
     */
    public void syncToLocalTime(String dateTime, String date, String time) {
        log.info("同步公安网时间：{}", dateTime);
        // 设置本地系统时间，例如2023年1月1日12时0分0秒
        try {
            String commandDate = "cmd /c date " + date;
            Process processDate = Runtime.getRuntime().exec(commandDate);
            processDate.waitFor();
            String commandTime = "cmd /c time " + time;
            Process processTime = Runtime.getRuntime().exec(commandTime);
            processTime.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.info("同步监管时间失败");
        }
    }

    /**
     * 报道前通用
     *
     * @param checkInBo 入住bo
     * @return {@link Boolean}
     */
    public Boolean beforeCheckIn(CheckInBo checkInBo) {
        // 判断此人是否在今日考试记录中报到
        if (recordsMapper.exists(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, checkInBo.getZjhm())
                        .eq(W2Records::getYkrq, DateUtil.today())
        )) {
            throw new SuccessException("考生已报到");
        }
        checkInBo.setKskm(configService.selectConfigByKey(CacheNames.COURSE_KEY));
        checkInBo.setZjzp(imageService.saveImage(checkInBo.getZjzp()));
        checkInBo.setJbzp(imageService.saveImage(checkInBo.getJbzp()));
        // 如果是摩托车考场  2,3  监管只会用 2
        boolean onlyCheckJg = true;// 只检查监管接口
        if (checkInBo.getKskm().contains(",")) { // 摩托车考场
            checkInBo.setKskm("2");
            // 先将手动导入的科目三报到
            onlyCheckJg = recordsMapper.update(null, Wrappers.lambdaUpdate(W2Records.class)
                    .eq(W2Records::getZjhm, checkInBo.getZjhm())
                    .eq(W2Records::getYkrq, DateUtil.today())
                    .eq(W2Records::getKskm, "3")
                    .set(W2Records::getZjzp, checkInBo.getZjzp())
                    .set(W2Records::getJbzp, checkInBo.getJbzp())
            ) <= 0 ? true : false;
        }
        return onlyCheckJg;
    }

    /**
     * 报道生成考生信息
     *
     * @param w2Records w2条记录
     * @param checkInBo 入住bo
     */
    public void checkInCreateW2Records(W2Records w2Records, CheckInBo checkInBo) {
        w2Records.setZjhm(checkInBo.getZjhm());
        w2Records.setZjzp(checkInBo.getZjzp());
        w2Records.setJbzp(checkInBo.getJbzp());
        w2Records.setKskm(checkInBo.getKskm());
        w2Records.setKsrq(DateUtil.beginOfDay(new Date()));
        w2Records.setKsjg("0");
        w2Records.setKscj1("0");
        w2Records.setKscj2("0");
        if (StringUtils.isBlank(w2Records.getXm())) w2Records.setXm(checkInBo.getXm());
        w2Records.setYkrq(DateUtil.today());
        w2Records.setKszt("0");
        if (recordsMapper.insert(w2Records) <= 0) {
            throw new FailException("录入考生信息失败");
        }
    }

    /**
     * 报道生成排队信息
     *
     * @param w2Queuing w2排队
     * @param w2Records w2考生记录
     */
    public void checkInCreateW2Queuing(W2Queuing w2Queuing, W2Records w2Records) {
        w2Queuing.setXm(w2Records.getXm());
        w2Queuing.setZjhm(w2Records.getZjhm());
        w2Queuing.setKscx(w2Records.getKscx());
        w2Queuing.setZkzh(w2Records.getZkzh());
        w2Queuing.setKskm(w2Records.getKskm());
        w2Queuing.setSign(0l);
        w2Queuing.setZt("0");
        w2Queuing.setKszt("0");
        w2Queuing.setDjc(0l);
        w2Queuing.setScore(0l);
        w2Queuing.setBdxh(0l);
        w2Queuing.setKg(w2Records.getKsy1());
        w2Queuing.setKg2(w2Records.getKsy2());
        w2Queuing.setKsrq(DateUtil.beginOfDay(new Date()));
        w2Queuing.setKscs(w2Records.getKscs());
        w2Queuing.setYycs(String.valueOf(w2Records.getYycs()));
        w2Queuing.setJxdm(w2Records.getJxdm());
        w2Queuing.setBcyykscs(w2Records.getBcyykscs());
        w2Queuing.setKsyy(w2Records.getKsyy());
        if (queuingMapper.insert(w2Queuing) <= 0) {
            throw new FailException("录入考生信息失败");
        }
    }

    /**
     * 签到前通用
     *
     * @param signInBo 登录bo
     * @return {@link Boolean}
     */
    public Boolean beforeSignIn(SignInBo signInBo) {
        String kskm = configService.selectConfigByKey(CacheNames.COURSE_KEY);
        boolean onlyCheckJg = true;// 只检查监管接口
        if (kskm.contains(",")) { // 摩托车考场
            signInBo.setKskm("2");
            // 先将手动导入的科目三签到
            onlyCheckJg = queuingMapper.update(null, Wrappers.lambdaUpdate(W2Queuing.class)
                    .eq(W2Queuing::getZjhm, signInBo.getZjhm())
                    .eq(W2Queuing::getKsrq, DateUtil.beginOfDay(new Date()))
                    .eq(W2Queuing::getKskm, "3")
                    .set(W2Queuing::getSign, 1l)
            ) <= 0 ? true : false;
        }
        return onlyCheckJg;
    }


    /**
     * 分车前通用
     *
     * @return {@link SplitCarDto}
     */
    public SplitCarDto beforeSplitCar() {
        // 获取可用考车信息
        LambdaQueryWrapper<W2Kcxx> kcLqw = Wrappers.lambdaQuery(W2Kcxx.class);
        kcLqw.eq(W2Kcxx::getZt, "1");
        List<W2KcxxVo> w2KcxxVos = kcxxMapper.selectVoList(kcLqw);
        SplitCarDto splitCarDto = new SplitCarDto();
        splitCarDto.setCphList(w2KcxxVos.stream().map(W2KcxxVo::getCph).collect(Collectors.toList()));
        if (splitCarDto.getCphList().size() == 0) throw new FailException("没有可分配的车辆");
        splitCarDto.setKcxxMap(w2KcxxVos.stream().collect(Collectors.toMap(W2KcxxVo::getCph, vo -> vo)));

        //车牌号对应考生的数量
        HashMap<String, Integer> kchMap = new HashMap<>();
        // 获取待为考试考生
        List<W2QueuingVo> queuingList = queuingMapper.selectVoList(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getSign, 1l)
                        .eq(W2Queuing::getKszt, "0")
                        .eq(W2Queuing::getSqks, 1l)
        );

        Iterator<W2QueuingVo> iterator = queuingList.iterator();
        while (iterator.hasNext()) {
            W2QueuingVo next = iterator.next();
            //删除已分车的学员
            if (next.getZt().equals("1")) {
                Integer num = kchMap.containsKey(next.getKchp()) ? kchMap.get(next.getKchp()) : 1;
                kchMap.put(next.getKchp(), num);

                iterator.remove();
            } else {
                splitCarDto.setKscc(next.getKscc());
                splitCarDto.setKgzjh(next.getKgname());
            }
        }
        splitCarDto.setKchMap(kchMap);
        // 没有待分的学员，返回
        if (queuingList.size() == 0) throw new SuccessException("没有待分车考生");

        // 获取最大分车人数
        splitCarDto.setSplitNum(Long.valueOf(configService.selectConfigByKey(CacheNames.SPLIT_NUM)));

        // 获取线路信息
        splitCarDto.setLineConfigMap(lineconfigService.getLineConfigMap(configService.selectConfigByKey(CacheNames.COURSE_KEY)));

        return splitCarDto;
    }

    /**
     * 处理分车结果
     *
     * @param kcxxVo        考车信息Vo
     * @param responseDto   分车结果
     * @param lineConfigMap 线路配置
     * @param splitNum      分车人数
     */
    public void handleSplitResult(W2KcxxVo kcxxVo, SplitCarServerResponseDto responseDto, Map<String, Long> lineConfigMap, Long splitNum) {
        String course = configService.selectConfigByKey(CacheNames.COURSE_KEY);
        if (StringUtils.isBlank(responseDto.getMessage()) && course.equals("2,3")) {
            //处理摩托车考场单独只考科三不上监管的分车情况
            // 获取待为考试考生
            List<W2QueuingVo> queuingList = queuingMapper.selectVoList(
                    Wrappers.lambdaQuery(W2Queuing.class)
                            .eq(W2Queuing::getSign, 1l)
                            .eq(W2Queuing::getKszt, "0")
                            .eq(W2Queuing::getSqks, 1l)
                            .eq(W2Queuing::getKskm, "3")
                            .eq(W2Queuing::getKsrq, DateUtil.beginOfDay(new Date()))
                            .orderByDesc(W2Queuing::getZt)
            );
            this.handleSplitResult(kcxxVo, splitNum, queuingList);
        } else {
            // 处理正常分车的结果
            Arrays.stream(responseDto.getMessage().split(",")).forEach(zjhm -> {
                // 修改排队分车状态
                W2Queuing w2Queuing = new W2Queuing();
                w2Queuing.setKcbh(kcxxVo.getKch());
                w2Queuing.setKchp(kcxxVo.getCph());

                String kskm = "2";
                if (StringUtils.isNotBlank(responseDto.getRetval())) {
                    w2Queuing.setRLine(lineConfigMap.get(responseDto.getRetval()));
                    kskm = "3";
                }
                w2Queuing.setZt("1");
                w2Queuing.setBdxh(RedisUtil.incrBdxh());

                // 处理根据考车分配考试项目问题（科目二、三）
                StringBuilder ksxm = new StringBuilder();
                if (!configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY).equals("0")) {
                    ksxm.append(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY));
                    ksxm.append(",");
                }
                if (course.equals("2,3") && kcxxVo.getXmxh().contains(";")) {
                    String[] split = kcxxVo.getXmxh().split(";");
                    ksxm.append(kskm.equals("2") ? split[0] : split[1]);
                } else {
                    ksxm.append(kcxxVo.getXmxh());
                }
                w2Queuing.setKsxm(ksxm.toString());
                w2Queuing.setKcxh(kcxxVo.getClxh());

                queuingMapper.update(w2Queuing,
                        Wrappers.lambdaUpdate(W2Queuing.class)
                                .eq(W2Queuing::getZjhm, zjhm)
                );
                // 修改成绩表
                W2Records w2Records = new W2Records();
                w2Records.setKcbh(kcxxVo.getKch());
                w2Records.setKchp(kcxxVo.getCph());
                w2Records.setKsxm(w2Queuing.getKsxm());
                w2Records.setLine(w2Queuing.getRLine());
                recordsMapper.update(w2Records, Wrappers.lambdaUpdate(W2Records.class).eq(W2Records::getZjhm, zjhm).eq(W2Records::getYkrq, DateUtil.today()));

            });
        }
    }

    /**
     * 处理分车结果
     *
     * @param kcxxVo      kcxxVo
     * @param splitNum    分车人数
     * @param queuingList 排队名单
     */
    public void handleSplitResult(W2KcxxVo kcxxVo, Long splitNum, List<W2QueuingVo> queuingList) {
        String course = configService.selectConfigByKey(CacheNames.COURSE_KEY);
        int queueNum = 0;
        while (queueNum < splitNum && queuingList.size() > 0) {
            queueNum++;
            //更新排队信息
            W2QueuingVo queuingVo = queuingList.get(0);
            W2Queuing w2Queuing = new W2Queuing();
            w2Queuing.setKcbh(kcxxVo.getKch());
            w2Queuing.setKchp(kcxxVo.getCph());
            if (queuingVo.getKskm().equals("3")) {
                w2Queuing.setRLine(kcxxVo.getRLine());
            }
            w2Queuing.setZt("1");
            w2Queuing.setBdxh(RedisUtil.incrBdxh());
            // 处理根据考车分配考试项目问题（科目二、三）
            StringBuilder ksxm = new StringBuilder();
            if (!configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY).equals("0")) {
                ksxm.append(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY));
                ksxm.append(",");
            }
            if (course.equals("2,3") && kcxxVo.getXmxh().contains(";")) {
                String[] split = kcxxVo.getXmxh().split(";");
                ksxm.append(queuingVo.getKskm().equals("2") ? split[0] : split[1]);
            } else {
                ksxm.append(kcxxVo.getXmxh());
            }
            w2Queuing.setKsxm(ksxm.toString());
            w2Queuing.setKcxh(kcxxVo.getClxh());

            if (queuingMapper.update(w2Queuing, Wrappers.lambdaUpdate(W2Queuing.class)
                    .eq(W2Queuing::getZjhm, queuingVo.getZjhm())
            ) > 1) {
                w2Queuing.setBdxh(RedisUtil.incrBdxh());
                queuingMapper.update(w2Queuing, Wrappers.lambdaUpdate(W2Queuing.class)
                        .eq(W2Queuing::getZjhm, queuingVo.getZjhm())
                        .eq(W2Queuing::getKskm, "3")
                );
            }

            // 更新考试成绩信息
            W2Records w2Records = new W2Records();
            w2Records.setKcbh(kcxxVo.getKch());
            w2Records.setKchp(kcxxVo.getCph());
            w2Records.setKsxm(queuingVo.getKsxm());
            w2Records.setLine(queuingVo.getRLine());
            recordsMapper.update(w2Records,
                    Wrappers.lambdaUpdate(W2Records.class)
                            .eq(W2Records::getZjhm, queuingVo.getZjhm())
                            .eq(W2Records::getYkrq, DateUtil.today())
            );
            // 删除这个队列
            queuingList.remove(0);

        }

    }
}
