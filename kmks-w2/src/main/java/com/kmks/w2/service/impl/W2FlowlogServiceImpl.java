package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.bo.AnalyseKfdmBo;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.vo.AnalyseKfdmVo;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.W2Flowlog;
import com.kmks.w2.mapper.W2FlowlogMapper;
import com.kmks.w2.service.IW2FlowlogService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考试过程信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-25
 */
@RequiredArgsConstructor
@Service
public class W2FlowlogServiceImpl implements IW2FlowlogService {

    private final W2FlowlogMapper baseMapper;

    private final W2RecordsMapper recordsMapper;

    /**
     * 查询考试过程信息
     */
    @Override
    public W2FlowVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询考试过程信息列表
     */
    @Override
    public TableDataInfo<W2FlowVo> queryPageList(W2FlowBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Flowlog> lqw = buildQueryWrapper(bo);
        Page<W2FlowVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询考试过程信息列表
     */
    @Override
    public List<W2FlowVo> queryList(W2FlowBo bo) {
        LambdaQueryWrapper<W2Flowlog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Flowlog> buildQueryWrapper(W2FlowBo bo) {
        LambdaQueryWrapper<W2Flowlog> lqw = Wrappers.lambdaQuery();
        Map<String, Object> params = bo.getParams();
        lqw.eq(StringUtils.isNotBlank(bo.getKsbh()), W2Flowlog::getKsbh, bo.getKsbh());
        lqw.eq(StringUtils.isNotBlank(bo.getKsxm()), W2Flowlog::getKsxm, bo.getKsxm());
        lqw.eq(bo.getKszt() != null, W2Flowlog::getKszt, bo.getKszt());
        lqw.eq(bo.getKssj() != null, W2Flowlog::getKssj, bo.getKssj());
        lqw.eq(bo.getKskf() != null, W2Flowlog::getKskf, bo.getKskf());
        lqw.eq(StringUtils.isNotBlank(bo.getKfdm()), W2Flowlog::getKfdm, bo.getKfdm());
        lqw.eq(StringUtils.isNotBlank(bo.getSbbh()), W2Flowlog::getSbbh, bo.getSbbh());
        lqw.eq(bo.getKscs() != null, W2Flowlog::getKscs, bo.getKscs());
        lqw.eq(StringUtils.isNotBlank(bo.getScbj()), W2Flowlog::getScbj, bo.getScbj());
        lqw.eq(StringUtils.isNotBlank(bo.getMsg()), W2Flowlog::getMsg, bo.getMsg());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2Flowlog::getKcbh, bo.getKcbh());
        lqw.eq(StringUtils.isNotBlank(bo.getZp()), W2Flowlog::getZp, bo.getZp());
        lqw.eq(StringUtils.isNotBlank(bo.getZpbs()), W2Flowlog::getZpbs, bo.getZpbs());
        lqw.eq(StringUtils.isNotBlank(bo.getZjhm()), W2Flowlog::getZjhm, bo.getZjhm());
        lqw.eq(StringUtils.isNotBlank(bo.getXyh()), W2Flowlog::getXyh, bo.getXyh());
        lqw.eq(StringUtils.isNotBlank(bo.getGadm()), W2Flowlog::getGadm, bo.getGadm());
        lqw.eq(StringUtils.isNotBlank(bo.getIcode()), W2Flowlog::getIcode, bo.getIcode());
        lqw.eq(StringUtils.isNotBlank(bo.getImessage()), W2Flowlog::getImessage, bo.getImessage());
        lqw.eq(StringUtils.isNotBlank(bo.getLsh()), W2Flowlog::getLsh, bo.getLsh());
        lqw.eq(StringUtils.isNotBlank(bo.getXtlb()), W2Flowlog::getXtlb, bo.getXtlb());
        lqw.eq(StringUtils.isNotBlank(bo.getJkid()), W2Flowlog::getJkid, bo.getJkid());
        lqw.eq(StringUtils.isNotBlank(bo.getUpxml()), W2Flowlog::getUpxml, bo.getUpxml());
        lqw.eq(StringUtils.isNotBlank(bo.getUpstatus()), W2Flowlog::getUpstatus, bo.getUpstatus());
        lqw.eq(StringUtils.isNotBlank(bo.getUpret()), W2Flowlog::getUpret, bo.getUpret());
        lqw.eq(StringUtils.isNotBlank(bo.getUpjpgxml()), W2Flowlog::getUpjpgxml, bo.getUpjpgxml());
        lqw.eq(StringUtils.isNotBlank(bo.getJkjpgid()), W2Flowlog::getJkjpgid, bo.getJkjpgid());
        lqw.eq(bo.getZpga() != null, W2Flowlog::getZpga, bo.getZpga());
        lqw.eq(StringUtils.isNotBlank(bo.getKskm()), W2Flowlog::getKskm, bo.getKskm());
        lqw.eq(StringUtils.isNotBlank(bo.getKchp()), W2Flowlog::getKchp, bo.getKchp());
        lqw.eq(bo.getKscj() != null, W2Flowlog::getKscj, bo.getKscj());
        lqw.eq(StringUtils.isNotBlank(bo.getXm()), W2Flowlog::getXm, bo.getXm());
        lqw.eq(StringUtils.isNotBlank(bo.getKsy1()), W2Flowlog::getKsy1, bo.getKsy1());
        lqw.eq(StringUtils.isNotBlank(bo.getKsy2()), W2Flowlog::getKsy2, bo.getKsy2());
        lqw.eq(StringUtils.isNotBlank(bo.getKsysfzhm1()), W2Flowlog::getKsysfzhm1, bo.getKsysfzhm1());
        lqw.eq(StringUtils.isNotBlank(bo.getKsysfzhm2()), W2Flowlog::getKsysfzhm2, bo.getKsysfzhm2());
        lqw.eq(bo.getAddtime() != null, W2Flowlog::getAddtime, bo.getAddtime());
        lqw.eq(StringUtils.isNotBlank(bo.getJkUpstatus()), W2Flowlog::getJkUpstatus, bo.getJkUpstatus());
        lqw.eq(StringUtils.isNotBlank(bo.getJkJpgupstatus()), W2Flowlog::getJkJpgupstatus, bo.getJkJpgupstatus());
        lqw.eq(StringUtils.isNotBlank(bo.getJudgeid()), W2Flowlog::getJudgeid, bo.getJudgeid());
        lqw.eq(StringUtils.isNotBlank(bo.getXmmc()), W2Flowlog::getXmmc, bo.getXmmc());
        lqw.eq(bo.getCurtime() != null, W2Flowlog::getCurtime, bo.getCurtime());
        lqw.eq(bo.getIYekao() != null, W2Flowlog::getIYekao, bo.getIYekao());
        lqw.eq(bo.getUptime() != null, W2Flowlog::getUptime, bo.getUptime());
        lqw.eq(bo.getBigzt() != null, W2Flowlog::getBigzt, bo.getBigzt());
        lqw.eq(StringUtils.isNotBlank(bo.getYkrq()), W2Flowlog::getYkrq, bo.getYkrq());
        lqw.eq(StringUtils.isNotBlank(bo.getFlag()), W2Flowlog::getFlag, bo.getFlag());
        lqw.and(params.get("beginKsrq") != null && params.get("endKsrq") != null,qw->{
            qw.between( W2Flowlog::getAddtime , DateUtil.parse(params.get("beginKsrq").toString(), "yyyy-MM-dd"), DateUtil.parse(params.get("endKsrq").toString()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        });
        return lqw;
    }

    private LambdaQueryWrapper<W2Records> buildRecordsQueryWrapper(AnalyseKfdmBo bo){
        LambdaQueryWrapper<W2Records> lqw = Wrappers.lambdaQuery();
        Map<String, Object> params = bo.getParams();
        lqw.eq(StringUtils.isNotBlank(bo.getJxdm()), W2Records::getJxdm, bo.getJxdm());
        lqw.eq(StringUtils.isNotBlank(bo.getKscx()), W2Records::getKscx, bo.getKscx());
        lqw.eq(StringUtils.isNotBlank(bo.getKsyy()), W2Records::getKsyy, bo.getKsyy());
        lqw.and(params.get("beginKsrq") != null && params.get("endKsrq") != null,qw->{
            qw.between( W2Records::getKsrq1 ,params.get("beginKsrq"), params.get("endKsrq")).or().between( W2Records::getKsrq2 ,params.get("beginKsrq"), params.get("endKsrq"));
        });
        return lqw;
    }
    @Override
    public List<AnalyseKfdmVo> getAnalyseKfxmVoList(AnalyseKfdmBo analyseKfdmBo) {
        W2FlowBo bo = BeanUtil.toBean(analyseKfdmBo, W2FlowBo.class);

        LambdaQueryWrapper<W2Flowlog> flowLoglqw = buildQueryWrapper(bo);
        flowLoglqw.isNotNull(StringUtils.isBlank(bo.getKfdm()),W2Flowlog::getKfdm);
        flowLoglqw.and(StringUtils.isNotBlank(analyseKfdmBo.getKsy()),qw->{
            qw.eq(W2Flowlog::getKsy1,analyseKfdmBo.getKsy()).or().eq(W2Flowlog::getKsy2,analyseKfdmBo.getKsy());
        });
        // 从驾校中查出考生编号
        if (StringUtils.isNotBlank(analyseKfdmBo.getJxdm()) || StringUtils.isNotBlank(analyseKfdmBo.getKscx()) || StringUtils.isNotBlank(analyseKfdmBo.getKsyy())){
            LambdaQueryWrapper<W2Records> w2RecordsLambdaQueryWrapper = buildRecordsQueryWrapper(analyseKfdmBo);
            w2RecordsLambdaQueryWrapper.select(W2Records::getKsbh);
            List<String> collect = recordsMapper.selectVoList(w2RecordsLambdaQueryWrapper, W2RecordsVo.class).stream().map(W2RecordsVo::getKsbh).collect(Collectors.toList());
            flowLoglqw.in(W2Flowlog::getKsbh,collect);
        }
        flowLoglqw.groupBy(W2Flowlog::getKfdm);
        List<AnalyseKfdmVo> w2FlowVos = baseMapper.getAnalyseKfdmList(flowLoglqw);
        Integer kscs = baseMapper.getSumKscs();
        for (AnalyseKfdmVo vo : w2FlowVos){
            double percentage = ((double) vo.getKfcs() / kscs) * 100;
            DecimalFormat df = new DecimalFormat("0.0000");
            vo.setKfl(df.format(percentage) + "%");
        }
        return w2FlowVos;
    }

    /**
     * 新增考试过程信息
     */
    @Override
    public Boolean insertByBo(W2FlowBo bo) {
        W2Flowlog add = BeanUtil.toBean(bo, W2Flowlog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改考试过程信息
     */
    @Override
    public Boolean updateByBo(W2FlowBo bo) {
        W2Flowlog update = BeanUtil.toBean(bo, W2Flowlog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Flowlog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除考试过程信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
