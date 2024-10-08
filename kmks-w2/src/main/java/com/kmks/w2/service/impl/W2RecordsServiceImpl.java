package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kmks.w2.domain.*;
import com.kmks.w2.domain.dto.PassRateTotalDto;
import com.kmks.w2.domain.dto.RecordsResetDto;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.mapper.*;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.service.IW2RecordsService;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 成绩管理Service业务层处理
 *
 * @author lynn
 * @date 2023-04-10
 */
@RequiredArgsConstructor
@Service
public class W2RecordsServiceImpl implements IW2RecordsService {

    private final W2RecordsMapper baseMapper;

    private final W2FlowlogMapper flowlogMapper;

    private final W2FlowrecMapper flowrecMapper;

    private final W2QueuhisMapper queuhisMapper;
    private final W2QueuingMapper queuingMapper;

    /**
     * 查询成绩管理
     */
    @Override
    public W2RecordsVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询成绩管理列表
     */
    @Override
    public TableDataInfo<W2RecordsVo> queryPageList(W2RecordsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Records> lqw = buildQueryWrapper(bo, true);
        Page<W2RecordsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询成绩管理列表
     */
    @Override
    public List<W2RecordsVo> queryList(W2RecordsBo bo) {
        LambdaQueryWrapper<W2Records> lqw = buildQueryWrapper(bo, true);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 分析合格率列表
     *
     * @param bo bo
     * @return {@link List}<{@link PassRateVo}>
     */
    @Override
    public List<PassRateVo> queryRateList(W2RecordsBo bo) {
        // 查询数据
        LambdaQueryWrapper<W2Records> lqw = buildQueryWrapper(bo, false);
        lqw.gt(W2Records::getKsjg, "0");

        lqw.select(W2Records::getJxdm, W2Records::getKsjg, W2Records::getTotalNum);
        lqw.groupBy(W2Records::getJxdm, W2Records::getKsjg);
        List<W2RecordsVo> w2RecordsVos = baseMapper.selectVoList(lqw);
        // group转成map格式
        Map<String, List<W2RecordsVo>> list = w2RecordsVos.stream().map(vo -> {
            if (StringUtils.isEmpty(vo.getJxdm())) {
                vo.setJxdm("0");
            }
            return vo;
        }).collect(Collectors.groupingBy(W2RecordsVo::getJxdm));
        List<PassRateVo> passRateVoList = new ArrayList<PassRateVo>();

        list.entrySet().stream()
                .forEach(entry -> {
                    List<W2RecordsVo> voList = entry.getValue();
                    PassRateVo passRateVo = new PassRateVo();
                    voList.forEach(vo -> {
                        passRateVo.addData(vo);
                    });
                    passRateVo.handlePassRate();
                    passRateVoList.add(passRateVo);
                });
        return passRateVoList;
    }

    /**
     * 查询成绩总合格率
     */
    @Override
    public List<PassRateTotalVo> queryRateTotalList(W2RecordsBo bo) {
        LambdaQueryWrapper<W2Records> lqw = buildQueryWrapper(bo, false);
        lqw.select(W2Records::getKscx, W2Records::getKsjg, W2Records::getTotalNum);
        lqw.groupBy(W2Records::getKsjg, W2Records::getKscx);
        PassRateTotalDto totalDto = new PassRateTotalDto("考试总人数");
        PassRateTotalDto passDto = new PassRateTotalDto("合格总人数");
        PassRateTotalDto unPassDto = new PassRateTotalDto("不合格总人数");
        PassRateTotalDto firPassDto = new PassRateTotalDto("一次合格人数");
        PassRateTotalDto secPassDto = new PassRateTotalDto("二次合格人数");
        PassRateTotalDto totalTimesDto = new PassRateTotalDto("考试总次数");

        for (W2RecordsVo vo : baseMapper.selectVoList(lqw)) {
            handleByResult(vo, totalDto, passDto, unPassDto, firPassDto, secPassDto, totalTimesDto);
        }

        return Arrays.asList(new PassRateTotalVo(totalDto), new PassRateTotalVo(passDto), new PassRateTotalVo(unPassDto), new PassRateTotalVo("总合格率", passDto, totalDto), new PassRateTotalVo(firPassDto), new PassRateTotalVo("一次合格率", firPassDto, totalDto), new PassRateTotalVo(secPassDto), new PassRateTotalVo("二次合格率", secPassDto, totalDto), new PassRateTotalVo(totalTimesDto));
    }

    /**
     * 查询成绩明细合格率
     */
    @Override
    public List<PassRateDetailVo> queryRateDetailList(W2RecordsBo bo) {
        LambdaQueryWrapper<W2Records> lqw = buildQueryWrapper(bo, false);
        lqw.select(W2Records::getJxdm, W2Records::getJxmc, W2Records::getKsjg, W2Records::getTotalNum);
        lqw.groupBy(W2Records::getKsjg, W2Records::getJxdm, W2Records::getJxmc);
        lqw.orderByAsc(W2Records::getJxdm);


        // 拼装map数据
        Map<String, PassRateDetailVo> map = new TreeMap<>();
        for (W2RecordsVo vo : baseMapper.selectVoList(lqw)) {
            if (ObjectUtil.isNull(vo.getJxdm())) continue;
            if (!map.containsKey(vo.getJxdm())) {
                PassRateDetailVo itemVo = new PassRateDetailVo();
                itemVo.setSchoolCode(vo.getJxdm());
                itemVo.setSchoolName(vo.getJxmc());
                map.put(vo.getJxdm(), itemVo);
            }
            handleByResultDetail(vo, map.get(vo.getJxdm()));
        }
        //转list
        PassRateDetailVo totalDto = new PassRateDetailVo();
        List<PassRateDetailVo> collect = map.values().stream().map(s -> {
            totalDto.AddNum(s.getPeopleCount(), s.getPeopleTimes(), s.getPassTotal(), s.getPassFir(), s.getPassSec());
            DecimalFormat df = new DecimalFormat("0.00");
            s.setPassRate(df.format((double) s.getPassTotal() / s.getPeopleCount() * 100) + "%");
            s.handlePassRate();
            return s;
        }).collect(Collectors.toList());

        //总数据
        totalDto.setSchoolCode("总计");
        totalDto.handlePassRate();
        collect.add(totalDto);


        return collect;

    }

    /**
     * 明细合格率根据考试结果处理
     *
     * @param vo       vo
     * @param detailVo 明细合格率对象
     */
    private void handleByResultDetail(W2RecordsVo vo, PassRateDetailVo detailVo) {
        //总人数
        detailVo.setPeopleCount(detailVo.getPeopleCount() + vo.getTotalNum());
        //根据考试结果判断
        switch (vo.getKsjg()) {
            case "1":
                //第一次合格
                detailVo.setPassFir(detailVo.getPassFir() + vo.getTotalNum());
                detailVo.setPeopleTimes(detailVo.getPeopleTimes() + vo.getTotalNum());
                detailVo.setPassTotal(detailVo.getPassTotal() + vo.getTotalNum());
                break;
            case "2":
                //第一次不合格
                detailVo.setPeopleTimes(detailVo.getPeopleTimes() + vo.getTotalNum());
                break;
            case "3":
                //第二次合格
                detailVo.setPassSec(detailVo.getPassSec() + vo.getTotalNum());
                detailVo.setPeopleTimes(detailVo.getPeopleTimes() + vo.getTotalNum() * 2);
                detailVo.setPassTotal(detailVo.getPassTotal() + vo.getTotalNum());
                break;
            case "4":
                //第二次不合格
                detailVo.setPeopleTimes(detailVo.getPeopleTimes() + vo.getTotalNum() * 2);
                break;
            default:
                break;
        }
    }

//    private void computeRate(PassRatetotalDto vo,PassRatetotalDto v1,PassRatetotalDto v2){
//        Class<? extends PassRatetotalDto> voClass = vo.getClass();
//        Class<? extends PassRatetotalDto> v1Class = v1.getClass();
//        Class<? extends PassRatetotalDto> v2Class = v2.getClass();
//        Arrays.asList("Total", "A1", "A2", "A3", "B1", "B2", "C1", "C2", "C5", "C6").forEach(a->{
//            try {
//                int value1 = (int)v1Class.getMethod("get"+a).invoke(v1);
//                if(value1 == 0){
//                    voClass.getMethod("set"+a,int.class).invoke(vo,0);
//                }else{
//                    int value2 = (int)v2Class.getMethod("get"+a).invoke(v2);
//                    voClass.getMethod("set"+a,int.class).invoke(vo,(int)Math.ceil(value2/Double.valueOf(value1)*10000));
//                }
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//
//    }

    /**
     * 根据考试结果处理数据
     *
     * @param vo            签证官
     * @param totalDto      总签证官
     * @param passDto       通过签证官
     * @param unPassDto     联合国通过签证官
     * @param firPassDto    冷杉通过签证官
     * @param secPassDto    证券交易委员会通过签证官
     * @param totalTimesDto 总时间签证官
     */
    private void handleByResult(W2RecordsVo vo, PassRateTotalDto totalDto, PassRateTotalDto passDto, PassRateTotalDto unPassDto, PassRateTotalDto firPassDto, PassRateTotalDto secPassDto, PassRateTotalDto totalTimesDto) {
        //总人数
        totalDto.setTotal(vo.getTotalNum());
        updateByCert(vo.getKscx(), totalDto, vo.getTotalNum());
        //根据考试结果判断
        switch (vo.getKsjg()) {
            case "1":
                //第一次合格
                updateByCert(vo.getKscx(), firPassDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), passDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), totalTimesDto, vo.getTotalNum());
                passDto.setTotal(vo.getTotalNum());
                firPassDto.setTotal(vo.getTotalNum());
                totalTimesDto.setTotal(vo.getTotalNum());
                break;
            case "2":
                //第一次不合格
                updateByCert(vo.getKscx(), unPassDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), totalTimesDto, vo.getTotalNum());
                unPassDto.setTotal(vo.getTotalNum());
                totalTimesDto.setTotal(vo.getTotalNum());

                break;
            case "3":
                //第二次合格
                updateByCert(vo.getKscx(), secPassDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), passDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), totalTimesDto, vo.getTotalNum() * 2);
                passDto.setTotal(vo.getTotalNum());
                secPassDto.setTotal(vo.getTotalNum());
                totalTimesDto.setTotal(vo.getTotalNum() * 2);

                break;
            case "4":
                //第二次不合格
                updateByCert(vo.getKscx(), unPassDto, vo.getTotalNum());
                updateByCert(vo.getKscx(), totalTimesDto, vo.getTotalNum() * 2);
                unPassDto.setTotal(vo.getTotalNum());
                totalTimesDto.setTotal(vo.getTotalNum() * 2);

                break;
            default:
                break;
        }
    }

    /**
     * 根据证型跟新相应证型的数据
     *
     * @param Kscx     kscx
     * @param totalDto vo
     */
    private void updateByCert(String Kscx, PassRateTotalDto totalDto, int times) {
        Class<? extends PassRateTotalDto> aClass = totalDto.getClass();

        try {
            aClass.getMethod("set" + Kscx, int.class).invoke(totalDto, times);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
//        switch(Kscx){
//            case "A1":
//                totalDto.setA1(times);
//                break;
//            case "A2":
//                totalDto.setA2(times);
//                break;
//            case "A3":
//                totalDto.setA3(times);
//                break;
//            case "B1":
//                totalDto.setB1(times);
//                break;
//            case "B2":
//                totalDto.setB2(times);
//                break;
//            case "C1":
//                totalDto.setC1(times);
//                break;
//            case "C2":
//                totalDto.setC2(times);
//                break;
//            case "C5":
//                totalDto.setC5(times);
//                break;
//            case "C6":
//                totalDto.setC6(times);
//                break;
//        }
    }

    private LambdaQueryWrapper<W2Records> buildQueryWrapper(W2RecordsBo bo, Boolean orderBy) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Records> lqw = Wrappers.lambdaQuery();
//        QueryWrapper lqw = Wrappers.query();

        lqw.like(StringUtils.isNotBlank(bo.getXm()), W2Records::getXm, bo.getXm());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2Records::getKcbh, bo.getKcbh());
        lqw.like(StringUtils.isNotBlank(bo.getZjhm()), W2Records::getZjhm, bo.getZjhm());
        lqw.eq(StringUtils.isNotBlank(bo.getKscx()), W2Records::getKscx, bo.getKscx());
        lqw.eq(StringUtils.isNotBlank(bo.getKsyy()), W2Records::getKsyy, bo.getKsyy());
        lqw.and(params.get("beginKsrq1") != null && params.get("endKsrq1") != null, qw -> {
//            qw.between( W2Records::getKsrq1 ,DateUtil.parse(params.get("beginKsrq1").toString()), DateUtil.parse(params.get("endKsrq1").toString() + " 23:59:59")).or().between( W2Records::getKsrq2 ,DateUtil.parse(params.get("beginKsrq1").toString()), DateUtil.parse(params.get("endKsrq1").toString() + " 23:59:59"));

            qw.between(W2Records::getKsrq, DateUtil.parse(params.get("beginKsrq1").toString()), DateUtil.parse(params.get("endKsrq1").toString() + " 23:59:59"));
        });
//        lqw.between(params.get("beginKsrq1") != null && params.get("endKsrq1") != null,
//            W2Records::getKsrq1 ,params.get("beginKsrq1"), params.get("endKsrq1"));
        lqw.and(StringUtils.isNotBlank(bo.getKsy1()), qw -> {
            qw.like(W2Records::getKsy1, bo.getKsy1()).or().like(W2Records::getKsy2, bo.getKsy1());
        });
//        lqw.like(StringUtils.isNotBlank(bo.getKsy1()), W2Records::getKsy1, bo.getKsy1());
        lqw.eq(StringUtils.isNotBlank(bo.getSfprint()), W2Records::getSfprint, bo.getSfprint());

        if (StringUtils.isNotBlank(bo.getKsjg())) {
            lqw.in(W2Records::getKsjg, bo.getKsjg().split(","));
        }
        lqw.eq(bo.getLine() != null, W2Records::getLine, bo.getLine());
        lqw.eq(StringUtils.isNotBlank(bo.getJxdm()), W2Records::getJxdm, bo.getJxdm());
        lqw.inSql(StringUtils.isNotBlank(bo.getSbbh()), W2Records::getKsbh, "SELECT KSBH FROM W2_FLOWREC WHERE SBBH = '" + bo.getSbbh() + "'");
        lqw.orderByDesc(orderBy, W2Records::getId);
        return lqw;
    }

    /**
     * 新增成绩管理
     */
    @Override
    public Boolean insertByBo(W2RecordsBo bo) {
        W2Records add = BeanUtil.toBean(bo, W2Records.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改成绩管理
     */
    @Override
    public Boolean updateByBo(W2RecordsBo bo) {
        W2Records update = BeanUtil.toBean(bo, W2Records.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Records entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除成绩管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Integer selectCountByGakfdm1(String gakfdm) {
        return baseMapper.selectCountByGakfdm1(gakfdm);
    }

    @Override
    public Integer selectCountByGakfdm2(String gakfdm) {
        return baseMapper.selectCountByGakfdm2(gakfdm);
    }

    @Override
    public Integer selectCountByKfxx1() {
        return baseMapper.selectCountByKfxx1();
    }

    @Override
    public Integer selectCountByKfxx2() {
        return baseMapper.selectCountByKfxx2();
    }

    @Override
    public List<String> selectSfzByZjhm() {
        return baseMapper.selectSfzByZjhm();
    }

    @Override
    public List<W2FlowVo> getFlowList(String zjhm, String ksrq) {
        // 当天的在rec表中  历史的在log中
        if (ksrq.equals(DateUtils.getDate())) {
            return flowrecMapper.getFlowListByDay(zjhm, ksrq);
        } else {
            return flowlogMapper.getFlowListByDay(zjhm, ksrq);
        }
    }

    // 误判申请
    @Override
    @Transactional
    public Boolean resetRecord(RecordsResetDto dto) {
        String updateSql = "";
        Long djc = 0l;
        ArrayList<Integer> kscsList = new ArrayList<>();
        if (ArrayUtil.contains(new int[]{2, 0}, dto.getWpxz())) {
            updateSql += "kscj2 = '重考',ksrq2 = null,kssj2 = null,jssj2 = null,jgfs2 = 0";
            djc = 2l;
            kscsList.add(2);
        }
        if (dto.getWpxz() == 0) {
            updateSql += ",";

        }
        if (ArrayUtil.contains(new int[]{1, 0}, dto.getWpxz())) {
            updateSql += "kscj1 = '重考',ksrq1 = null,kssj1 = null,jssj1 = null,jgfs1 = 0";
            djc = 1l;
            kscsList.add(1);
        }
        if (baseMapper.update(null, Wrappers.<W2Records>lambdaUpdate().setSql(updateSql)
                .eq(W2Records::getId, dto.getId())
        ) > 0) {
            W2RecordsVo w2RecordsVo = baseMapper.selectVoById(dto.getId());
            if (w2RecordsVo == null) throw new FailException("未找到该记录");

            //删除考试过程表信息
            flowlogMapper.delete(Wrappers.<W2Flowlog>lambdaQuery().eq(W2Flowlog::getKsbh, w2RecordsVo.getKsbh()).eq(W2Flowlog::getZjhm, w2RecordsVo.getZjhm()).in(W2Flowlog::getKscs, kscsList));
            flowrecMapper.delete(Wrappers.<W2Flowrec>lambdaQuery().eq(W2Flowrec::getKsbh, w2RecordsVo.getKsbh()).eq(W2Flowrec::getZjhm, w2RecordsVo.getZjhm()).in(W2Flowrec::getKscs, kscsList));

            // 查询排队历史表信息
            LambdaQueryWrapper<W2Queuhis> hisLqw = Wrappers.<W2Queuhis>lambdaQuery().eq(W2Queuhis::getKsbh, w2RecordsVo.getKsbh()).eq(W2Queuhis::getZjhm, w2RecordsVo.getZjhm());
            W2QueuhisVo w2QueuhisVo = queuhisMapper.selectVoOne(hisLqw);
            if (w2QueuhisVo == null) throw new FailException("缺少该考生历史排队信息");
            // 删除该考生排队历史信息
            queuhisMapper.delete(hisLqw);

            // 重新插入排队表中
            W2Queuing w2Queuing = BeanUtil.toBean(w2QueuhisVo, W2Queuing.class);
            w2Queuing.setId(null);
            w2Queuing.setWcxm(null);
            w2Queuing.setDjc(djc);
            w2Queuing.setKszt("0");
            w2Queuing.setKscj(0l);
            return queuingMapper.insert(w2Queuing) > 0;
        }
        return false;
    }
}
