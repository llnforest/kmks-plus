package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.bo.*;
import com.kmks.jianguan.domain.vo.*;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.domain.W2Flowrec;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.mapper.W2FlowrecMapper;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.service.*;
import com.kmks.w2.utils.FileUtil;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 15:18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements ICarService {
    private final IFaceService faceService;
    private final IJgService jgService;
    private final IImageService imageService;
    private final ISysConfigService configService;
    private final IW2LineconfigService lineconfigService;
    private final IW2CdxmbhService cdxmbhService;

    private final IW2KcxxService kcxxService;

    private final W2QueuingMapper queuingMapper;

    private final W2RecordsMapper recordsMapper;

    private final W2FlowrecMapper flowrecMapper;
    private final IW2KfconfigService kfconfigService;

    private final static String sczt = "上传成功";

    /**
     * 考车上线
     *
     * @param kcbh kcbh
     * @return {@link W2KcxxVo}
     */
    @Override
    public W2KcxxVo carOnLine(String kcbh){
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(kcbh);
        w2KcxxBo.setZt("1");
        W2KcxxVo w2KcxxVo = kcxxService.queryOne(w2KcxxBo);
        if(w2KcxxVo == null){
            throw new FailException("考车不存在或非可用状态");
        }
        return w2KcxxVo;
    }
    /**
     * 获取下一个考生
     *
     * @param kcbh kcbh
     * @return {@link W2QueuingVo}
     */
    @Override
    public W2QueuingVo getNextStudent(String kcbh){
        List<W2QueuingVo> w2QueuingVos = queuingMapper.selectVoList(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getKcbh, kcbh)
                        .eq(W2Queuing::getZt, "1")
                        .eq(W2Queuing::getKszt, "0")
                        .eq(W2Queuing::getKsrq,DateUtil.beginOfDay(new Date()))
                        .orderByAsc(W2Queuing::getBdxh)
        );
        if(w2QueuingVos == null || w2QueuingVos.isEmpty()){
            throw new FailException("没有下一个考生");
        }
        return w2QueuingVos.get(0);
    }

    /**
     * 根据证件获取证件照片与拍照照片比对结果
     *
     * @param zjhm zjhm
     * @param pzzp pzpp
     * @return {@link String}
     */
    @Override
    public R<Void> getFaceRecognizeResult(String zjhm, String pzzp){
        W2RecordsVo w2RecordsVo = getTodayStudent(zjhm);
        String jbzp = FileUtil.convertImageToBase64(w2RecordsVo.getJbzp());
        FaceRecognizeBo faceRecognizeBo = new FaceRecognizeBo();
        faceRecognizeBo.setZjhm(zjhm);
        faceRecognizeBo.setJbzp(jbzp);
        faceRecognizeBo.setZjzp(pzzp);
        Boolean similar = faceService.faceRecognize(faceRecognizeBo);
        return similar ?R.ok("比对成功"):R.fail("比对失败");

    }

    /**
     * 获取当日考生信息
     *
     * @param zjhm zjhm
     * @return {@link W2RecordsVo}
     */
    @Override
    public W2RecordsVo getTodayStudent(String zjhm){
        W2RecordsVo w2RecordsVo = recordsMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, zjhm)
                        .eq(W2Records::getYkrq, DateUtil.today())
        );
        if(w2RecordsVo == null){
            throw new FailException("今日无此考生");
        }
        return w2RecordsVo;
    }

    /**
     * 申请考试
     *
     * @param kcbh kcbh
     * @param kssj kssj
     * @param zjhm zjhm
     * @param kskm kskm
     * @param zp   zp
     * @return {@link W2QueuingVo}
     */
    @Override
//    @Transactional("masterTransactionManager")
    public W2QueuingVo applyExam(String kcbh, String kssj,String zjhm,String kskm,String zp){
        // 验证车辆排队等基础信息
        W2QueuingVo queuingVo = judgeCarAndQueuing(kcbh, zjhm);
        // 发送开始考试至监管
        A0221000009Bo a0221000009Bo = new A0221000009Bo();
        a0221000009Bo.setSfzmhm(zjhm);
        a0221000009Bo.setKchp(kcbh);
        a0221000009Bo.setKskm(kskm);
        a0221000009Bo.setKssj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if(kskm.equals("3")){
            a0221000009Bo.setKsxl(lineconfigService.getLineDm(queuingVo.getRLine()));
        }
        A0221000009Vo a0221000009Vo = jgService.a0221000009(a0221000009Bo, zp);
        handleJgResult(kcbh,a0221000009Vo.getCode().equals("1"),String.format("监管开始考试失败：%s；%s；%s",a0221000009Vo.getCode(),a0221000009Vo.getMessage(),a0221000009Vo.getRetval()));

        // 更新排队数据
        if(!queuingVo.getKszt().equals("1")){
            queuingVo.setDjc(queuingVo.getDjc() +1);
        }
        if(queuingVo.getDjc() > 2l){
            throw new FailException("已超过2次考试");
        }
        queuingVo.setKszt("1");
        queuingVo.setWcxm("");
        queuingVo.setScore(100l);
        if(queuingMapper.updateById(BeanUtil.toBean(queuingVo,W2Queuing.class)) <= 0){
            throw new FailException("更新排队数据失败");
        }
        //更新成绩数据
        W2RecordsVo w2RecordsVo = recordsMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, zjhm)
                        .eq(W2Records::getKcbh, kcbh)
                        .eq(W2Records::getKsrq, DateUtil.beginOfDay(new Date()))
                        .orderByDesc(W2Records::getId)

        );
        W2Records w2Records = BeanUtil.toBean(w2RecordsVo, W2Records.class);
        w2Records.setKscs(queuingVo.getDjc());
        if(w2Records.getKscs() == 1l){
            w2Records.setKsrq1(DateUtil.beginOfDay(new Date()));
            w2Records.setKssj1(DateUtil.date());
            w2Records.setJgfs1(100l);
            w2Records.setKscj1("3");
        }else{
            w2Records.setKsrq2(DateUtil.beginOfDay(new Date()));
            w2Records.setKssj2(DateUtil.date());
            w2Records.setJgfs2(100l);
            w2Records.setKscj2("3");
        }
        w2Records.setKszt("1");

        if(recordsMapper.updateById(w2Records) <= 0){
            throw new FailException("考试成绩信息更新失败");
        }

        //新增考试明细数据
        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingVo, zp, kssj, "申请考试");

        // 插入开始考试记录
        insertFlowrec(w2Flowrec);

        //更新监管剩余项目
        String ksxm = cdxmbhService.getMdmByGadm(new ArrayList<>(Arrays.asList(a0221000009Vo.getRetval().split(","))));
        queuingVo.setKsxm(ksxm);
        return queuingVo;
    }


    /**
     * 开始考试
     *
     * @param kcbh kcbh
     * @param kssj kssj
     * @param zjhm zjhm
     * @param zp   zp
     * @return {@link W2QueuingVo}
     */
    @Override
//    @Transactional("masterTransactionManager")
    public W2QueuingVo startExam(String kcbh, String kssj, String zjhm, String zp,String speed){
        // 验证车辆排队等基础信息
        W2QueuingVo queuingVo = judgeCarAndQueuing(kcbh, zjhm);
        // 清空已存在的考试明细
        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingVo, zp, kssj, "开始考试");

        // 插入申请考试记录
        insertFlowrec(w2Flowrec);
        return queuingVo;
    }

    /**
     * 项目开始
     *
     * @param kcbh    kcbh
     * @param kssj    kssj
     * @param zjhm    zjhm
     * @param fieldId 字段id
     * @param zp      zp
     * @param speed   速度
     * @return {@link String}
     */
    @Override
//    @Transactional
    public String startProgram(String kcbh, String kssj, String zjhm, String fieldId, String zp, String speed){
        // 获取考生排队信息
        W2QueuingVo queuingInfo = getQueuingInfo(kcbh, zjhm);

        // 上传项目开始至监管
        A0221000010Bo a0221000010Bo = new A0221000010Bo();
        a0221000010Bo.setSfzmhm(zjhm);
        a0221000010Bo.setKchp(queuingInfo.getKchp());
        a0221000010Bo.setKskm(queuingInfo.getKskm());
        W2CdxmbhVo cdxmConfig = cdxmbhService.getCdxmConfig(fieldId);
        a0221000010Bo.setKsxm(cdxmConfig.getGadm());

        if(a0221000010Bo.getKskm().equals("2")){
            a0221000010Bo.setSbbh(cdxmConfig.getSbxh());
        }else{
            String lineDm = lineconfigService.getLineDm(Long.valueOf(fieldId));
            a0221000010Bo.setKsxl(lineDm);
        }
        a0221000010Bo.setKssj(LocalDateTime.parse(kssj,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        A0221000010Vo a0221000010Vo = jgService.a0221000010(a0221000010Bo);

        handleJgResult(kcbh,a0221000010Vo.getCode().equals("1"),String.format("上传监管项目开始失败：%s；%s",a0221000010Vo.getCode(),a0221000010Vo.getMessage()));

        // 上传考试过程图片至监管
        A0221000012Bo a0221000012Bo = new A0221000012Bo();
        a0221000012Bo.setKskm(a0221000010Bo.getKskm());
        a0221000012Bo.setZpsj(a0221000010Bo.getKssj());
        a0221000012Bo.setSfzmhm(zjhm);
        a0221000012Bo.setKskm(cdxmConfig.getGadm());
        a0221000012Bo.setCs(Double.valueOf(speed));
        A0221000012Vo a0221000012Vo = jgService.a0221000012(a0221000012Bo, zp);

        handleJgResult(kcbh,a0221000012Vo.getCode().equals("1"),String.format(String.format("上传监管考试过程图片失败：%s；%s",a0221000012Vo.getCode(),a0221000012Vo.getMessage())));

        //新增考试明细数据
        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingInfo, zp, kssj, "项目开始");
        w2Flowrec.setKsxm(fieldId);
        w2Flowrec.setXmmc(cdxmConfig.getParamname());
        w2Flowrec.setSbbh(cdxmConfig.getSbxh());
        w2Flowrec.setGadm(cdxmConfig.getGadm());

        // 插入开始考试记录
        insertFlowrec(w2Flowrec);
        return cdxmConfig.getParamname();
    }


    @Override
//    @Transactional
    public W2QueuingVo finishProgram(String kcbh, String kssj, String zjhm, String fieldId, String zp, String speed){
        // 获取考生排队信息
        W2QueuingVo queuingInfo = getQueuingInfo(kcbh, zjhm);

        // 上传考试结束至监管
        A0221000013Bo a0221000013Bo = new A0221000013Bo();
        a0221000013Bo.setSfzmhm(zjhm);
        a0221000013Bo.setKskm(queuingInfo.getKskm());
        W2CdxmbhVo cdxmConfig = cdxmbhService.getCdxmConfig(fieldId);
        a0221000013Bo.setKsxm(cdxmConfig.getGadm());
        if(a0221000013Bo.getKskm().equals("2")){
            a0221000013Bo.setSbbh(cdxmConfig.getSbxh());
        }else{
            String lineDm = lineconfigService.getLineDm(Long.valueOf(fieldId));
            a0221000013Bo.setKsxl(lineDm);
        }
        a0221000013Bo.setJssj(LocalDateTime.parse(kssj,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000013Bo.setCzlx("1");
        A0221000013Vo a0221000013Vo = jgService.a0221000013(a0221000013Bo);

        handleJgResult(kcbh,a0221000013Vo.getCode().equals("1"),String.format(String.format("上传监管项目结束失败：%s；%s",a0221000013Vo.getCode(),a0221000013Vo.getMessage())));

        //新增考试明细数据
        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingInfo, zp, kssj, "项目结束");
        w2Flowrec.setKsxm(fieldId);
        w2Flowrec.setXmmc(cdxmConfig.getParamname());
        w2Flowrec.setSbbh(cdxmConfig.getSbxh());
        w2Flowrec.setGadm(cdxmConfig.getGadm());

        // 插入开始考试记录
        insertFlowrec(w2Flowrec);

        //获取完成项目
        String wcxm = getWcxm(cdxmConfig.getMdm(), queuingInfo);
        if(wcxm.equals(queuingInfo.getWcxm())){
            return queuingInfo;
        }
        queuingInfo.setWcxm(wcxm);
//        //考试结束
//        if(queuingInfo.getKsxm().equals(queuingInfo.getWcxm())){
//            queuingInfo.setKszt("2");
//            // 考试结束业务处理
//            finishExam(queuingInfo, kssj, zp, speed);
//        }
        if(queuingMapper.updateById(BeanUtil.toBean(queuingInfo,W2Queuing.class)) > 0){
            return queuingInfo;
        }
        throw new FailException("更新排队信息失败");
    }


    /**
     * 考试结束
     *
     * @param kcbh  kcbh
     * @param kssj  kssj
     * @param zjhm  zjhm
     * @param score 分数
     * @param zp    zp
     * @param speed 速度
     * @return {@link W2QueuingVo}
     */
    @Override
//    @Transactional
    public W2QueuingVo finishExam(String kcbh, String kssj, String zjhm, String score, String zp, String speed){
        // 获取考生排队信息
        W2QueuingVo queuingInfo = getQueuingInfo(kcbh, zjhm);
        // 上传考试结束至监管
        A0221000014Bo a0221000014Bo = new A0221000014Bo();
        a0221000014Bo.setSfzmhm(zjhm);
        a0221000014Bo.setKskm(queuingInfo.getKskm());
        a0221000014Bo.setJssj(LocalDateTime.parse(kssj,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000014Bo.setKscj(score);
        A0221000014Vo a0221000014Vo = jgService.a0221000014(a0221000014Bo,zp);

        handleJgResult(kcbh,a0221000014Vo.getCode().equals("1"),String.format(String.format("上传监管考试结束失败：%s；%s",a0221000014Vo.getCode(),a0221000014Vo.getMessage())));

        // 插入结束考试记录
        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingInfo, zp, kssj, "考试结束");
        insertFlowrec(w2Flowrec);

        // 更新排队信息
        queuingInfo.setScore(Long.valueOf(score));
        queuingInfo.setKszt("2");

        // 处理考试成绩表
        W2RecordsVo recordsInfo = getRecordsInfo(queuingInfo.getKcbh(), queuingInfo.getZjhm());
        recordsInfo.setKszt("2");
        //评判考试结果
        Long qualifiedScore = Long.valueOf(configService.selectConfigByKey(CacheNames.QUALIFIED_SCORE_KEY));
        if(recordsInfo.getKscs() == 1l){
            recordsInfo.setJssj1(DateUtil.parse(kssj));
            if(recordsInfo.getJgfs1() >= qualifiedScore){
                recordsInfo.setKsjg("1");
                recordsInfo.setKscj1("1");
                queuingInfo.setKscj(1l);
            }else{
                recordsInfo.setKsjg("2");
                recordsInfo.setKscj1("2");
                queuingInfo.setKscj(2l);
            }

        }else{
            recordsInfo.setJssj2(DateUtil.parse(kssj));
            if(recordsInfo.getJgfs2() >= qualifiedScore){
                recordsInfo.setKsjg("1");
                recordsInfo.setKscj1("3");
                queuingInfo.setKscj(3l);
            }else{
                recordsInfo.setKsjg("2");
                recordsInfo.setKscj1("4");
                queuingInfo.setKscj(4l);
            }
        }

        if(queuingMapper.updateById(BeanUtil.toBean(queuingInfo,W2Queuing.class)) <= 0){
            throw new FailException("更新排队信息失败");
        }

        if(recordsMapper.updateById(BeanUtil.toBean(recordsInfo,W2Records.class))>0){
            return queuingInfo;
        }
        throw new FailException("结束考试更新成绩数据失败");
    }

    /**
     * 扣分
     * 上传扣分
     *
     * @param kcbh    kcbh
     * @param kssj    kssj
     * @param zjhm    zjhm
     * @param fieldId 字段id
     * @param kfdm    kfdm
     * @param kfType  kf型
     * @param zp      zp
     * @param speed   速度
     * @return {@link W2QueuingVo}
     */
    @Override
//    @Transactional
    public W2QueuingVo deductPoint(String kcbh, String kssj, String zjhm, String fieldId, String kfdm, String kfType, String zp, String speed){
        // 获取考生排队信息
        W2QueuingVo queuingInfo = getQueuingInfo(kcbh, zjhm);
        W2CdxmbhVo cdxmConfig = cdxmbhService.getCdxmConfig(fieldId);
        // 上传考试扣分至监管
        A0221000011Bo a0221000011Bo = new A0221000011Bo();
        a0221000011Bo.setSfzmhm(zjhm);
        a0221000011Bo.setKskm(queuingInfo.getKskm());
        a0221000011Bo.setKfsj(LocalDateTime.parse(kssj,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000011Bo.setKsxm(cdxmConfig.getGadm());
        a0221000011Bo.setKfxm(kfdm);
        a0221000011Bo.setKffs(kfType);
        A0221000011Vo a0221000011Vo = jgService.a0221000011(a0221000011Bo);
        handleJgResult(kcbh,a0221000011Vo.getCode().equals("1"),String.format(String.format("上传监管考试扣分失败：%s；%s",a0221000011Vo.getCode(),a0221000011Vo.getMessage())));

        // 上传考试过程图片至监管
        A0221000012Bo a0221000012Bo = new A0221000012Bo();
        a0221000012Bo.setKskm(a0221000011Bo.getKskm());
        a0221000012Bo.setZpsj(a0221000011Bo.getKfsj());
        a0221000012Bo.setSfzmhm(zjhm);
        a0221000012Bo.setKskm(cdxmConfig.getGadm());
        a0221000012Bo.setCs(Double.valueOf(speed));
        A0221000012Vo a0221000012Vo = jgService.a0221000012(a0221000012Bo, zp);
        handleJgResult(kcbh,a0221000012Vo.getCode().equals("1"),String.format(String.format("上传监管考试过程图片失败：%s；%s",a0221000012Vo.getCode(),a0221000012Vo.getMessage())));

        // 插入考试扣分记录

        W2Flowrec w2Flowrec = convertToW2Flowrec(queuingInfo, zp, kssj, "评判扣分");
        w2Flowrec.setKsxm(fieldId);
        w2Flowrec.setXmmc(cdxmConfig.getParamname());
        w2Flowrec.setSbbh(cdxmConfig.getSbxh());
        w2Flowrec.setGadm(cdxmConfig.getGadm());
        w2Flowrec.setKfdm(kfdm);
        W2KfconfigVo kfConfig = kfconfigService.getKfConfig(kfdm);
        w2Flowrec.setKskf(kfConfig.getKf());
        w2Flowrec.setMsg(kfConfig.getGakfmc());
        insertFlowrec(w2Flowrec);

        // 更新排队信息
        Long score = (queuingInfo.getScore()-kfConfig.getKf()) > 0?(queuingInfo.getScore()-kfConfig.getKf()):0l;
        queuingInfo.setScore(score);

        if(queuingMapper.updateById(BeanUtil.toBean(queuingInfo,W2Queuing.class)) <= 0){
            throw new FailException("更新排队信息失败");
        }

        // 处理考试成绩表
        W2RecordsVo recordsInfo = getRecordsInfo(queuingInfo.getKcbh(), queuingInfo.getZjhm());

        if(recordsInfo.getKscs() == 1l){
            recordsInfo.setJgfs1(score);
            recordsInfo.setKfxx1(StringUtils.isBlank(recordsInfo.getKfxx1()) ? kfdm : recordsInfo.getKfxx1()+";"+kfdm);
        }else{
            recordsInfo.setJgfs2(score);
            recordsInfo.setKfxx2(StringUtils.isBlank(recordsInfo.getKfxx2()) ? kfdm : recordsInfo.getKfxx2()+";"+kfdm);
        }
        if(recordsMapper.updateById(BeanUtil.toBean(recordsInfo,W2Records.class)) > 0){
            return queuingInfo;
        }
        throw new FailException("扣分成绩更新成绩数据失败");
    }


    /**
     * 获取完成项目
     *
     * @param xm        xm
     * @param queuingVo 排队vo
     * @return {@link String}
     */
    private String getWcxm(String xm,W2QueuingVo queuingVo){
        String wcxm = queuingVo.getWcxm();
        ArrayList<String> wcxms = new ArrayList<>();
        if(StringUtils.isNotBlank(wcxm)){
            wcxms = new ArrayList<>(Arrays.asList(wcxm.split(",")));
        }
        if(wcxms.contains(xm)){
            return wcxm;
        }else{
            wcxms.add(xm);
            String[] ksxms = queuingVo.getKsxm().split(",");
            if(wcxms.size() == ksxms.length -1 && !wcxms.contains(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY))){
                wcxms.add(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY));
            }
            Collections.sort(wcxms);
            return StringUtils.join(wcxms, ",");

        }

    }

    /**
     * 是否允许考试基础信息评判
     *
     * @param kcbh kcbh
     * @param zjhm zjhm
     * @return {@link W2QueuingVo}
     */
    private W2QueuingVo judgeCarAndQueuing(String kcbh,String zjhm){
        // 判断考车状态
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(kcbh);
        w2KcxxBo.setZt("1");
        if(!kcxxService.exists(w2KcxxBo)){
            throw new FailException("考车状态不可用");
        }
        // 判断是否允许学员考试
        W2QueuingVo queuingVo = queuingMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getZjhm, zjhm)
                        .eq(W2Queuing::getKcbh, kcbh)
                        .eq(W2Queuing::getKsrq,DateUtil.beginOfDay(new Date()))
        );
        if(queuingVo == null){
            throw new FailException("不存在该考生或考生状态不允许开始考试");
        }
        return queuingVo;
    }

    /**
     * 获取排队信息
     *
     * @param kcbh kcbh
     * @param zjhm zjhm
     * @return {@link W2QueuingVo}
     */
    private W2QueuingVo getQueuingInfo(String kcbh,String zjhm){
        // 获取正在考试中的排队信息
        W2QueuingVo queuingVo = queuingMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getZjhm, zjhm)
                        .eq(W2Queuing::getKcbh, kcbh)
                        .eq(W2Queuing::getKsrq,DateUtil.beginOfDay(new Date()))
                        .eq(W2Queuing::getZt, "1")
                        .eq(W2Queuing::getKszt,"1")
        );
        if(queuingVo == null){
            throw new FailException("未查询到考生排队信息");
        }
        return queuingVo;
    }

    /**
     * 获取考生成绩信息
     *
     * @param kcbh kcbh
     * @param zjhm zjhm
     * @return {@link W2RecordsVo}
     */
    private W2RecordsVo getRecordsInfo(String kcbh,String zjhm){
        // 获取正在考试中的成绩信息
        W2RecordsVo w2RecordsVo = recordsMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, zjhm)
                        .eq(W2Records::getKcbh, kcbh)
                        .eq(W2Records::getKsrq, DateUtil.today())
                        .orderByDesc(W2Records::getId)

        );
        if(w2RecordsVo == null){
            throw new FailException("未查询到考生成绩信息");
        }
        return w2RecordsVo;
    }

    /**
     * 转换为w2Flowrec
     *
     * @param queuingVo 排队vo
     * @param zp        zp
     * @param kssj      kssj
     * @param kszt      kszt
     * @return {@link W2Flowrec}
     */
    private W2Flowrec convertToW2Flowrec(W2QueuingVo queuingVo,String zp,String kssj,String kszt){
        W2Flowrec w2Flowrec = new W2Flowrec();

        if(StringUtils.isNotBlank(zp)){
            w2Flowrec.setZp(imageService.saveImage(zp));
        }

        w2Flowrec.setKssj(DateUtil.parse(kssj));
        w2Flowrec.setYkrq(DateUtil.today());
        w2Flowrec.setKszt(kszt);
        w2Flowrec.setKscs(queuingVo.getDjc());

        w2Flowrec.setKcbh(queuingVo.getKcbh());
        w2Flowrec.setKchp(queuingVo.getKchp());
        w2Flowrec.setZjhm(queuingVo.getZjhm());
        w2Flowrec.setXm(queuingVo.getXm());
        w2Flowrec.setKskm(queuingVo.getKskm());

        return w2Flowrec;

    }

    /**
     * 插入考试明细
     *
     * @param w2Flowrec w2Flowrec
     */
    private void insertFlowrec(W2Flowrec w2Flowrec){
        if(flowrecMapper.insert(w2Flowrec) <= 0){
            throw new FailException("插入考试明细失败");
        }
    }

    /**
     * 处理上传监管结果处理
     *
     * @param kcbh         kcbh
     * @param result       后果
     * @param errorMessage 错误消息
     */
    private void handleJgResult(String kcbh,Boolean result,String errorMessage){
        TcpUtils.setDispatchCenterDtoSczt(kcbh,result?sczt:errorMessage);
        if(!result){
            throw new FailException(errorMessage);
        }
    }
}
