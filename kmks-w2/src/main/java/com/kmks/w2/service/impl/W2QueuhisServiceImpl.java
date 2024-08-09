package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2QueuhisBo;
import com.kmks.w2.domain.vo.W2QueuhisVo;
import com.kmks.w2.domain.W2Queuhis;
import com.kmks.w2.mapper.W2QueuhisMapper;
import com.kmks.w2.service.IW2QueuhisService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@RequiredArgsConstructor
@Service
public class W2QueuhisServiceImpl implements IW2QueuhisService {

    private final W2QueuhisMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public W2QueuhisVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<W2QueuhisVo> queryPageList(W2QueuhisBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Queuhis> lqw = buildQueryWrapper(bo);
        Page<W2QueuhisVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<W2QueuhisVo> queryList(W2QueuhisBo bo) {
        LambdaQueryWrapper<W2Queuhis> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Queuhis> buildQueryWrapper(W2QueuhisBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Queuhis> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getKsbh()), W2Queuhis::getKsbh, bo.getKsbh());
        lqw.eq(StringUtils.isNotBlank(bo.getXm()), W2Queuhis::getXm, bo.getXm());
        lqw.eq(StringUtils.isNotBlank(bo.getZjhm()), W2Queuhis::getZjhm, bo.getZjhm());
        lqw.eq(StringUtils.isNotBlank(bo.getZkzh()), W2Queuhis::getZkzh, bo.getZkzh());
        lqw.eq(StringUtils.isNotBlank(bo.getKscx()), W2Queuhis::getKscx, bo.getKscx());
        lqw.eq(StringUtils.isNotBlank(bo.getYkxms()), W2Queuhis::getYkxms, bo.getYkxms());
        lqw.eq(StringUtils.isNotBlank(bo.getZkxms()), W2Queuhis::getZkxms, bo.getZkxms());
        lqw.eq(bo.getBdxh() != null, W2Queuhis::getBdxh, bo.getBdxh());
        lqw.eq(StringUtils.isNotBlank(bo.getKszt()), W2Queuhis::getKszt, bo.getKszt());
        lqw.eq(bo.getKscs() != null, W2Queuhis::getKscs, bo.getKscs());
        lqw.eq(bo.getYkcs() != null, W2Queuhis::getYkcs, bo.getYkcs());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2Queuhis::getKcbh, bo.getKcbh());
        lqw.eq(StringUtils.isNotBlank(bo.getWcxms()), W2Queuhis::getWcxms, bo.getWcxms());
        lqw.eq(bo.getKscj() != null, W2Queuhis::getKscj, bo.getKscj());
        lqw.eq(bo.getKssj() != null, W2Queuhis::getKssj, bo.getKssj());
        lqw.eq(StringUtils.isNotBlank(bo.getCdxmbh()), W2Queuhis::getCdxmbh, bo.getCdxmbh());
        lqw.eq(StringUtils.isNotBlank(bo.getSfyz()), W2Queuhis::getSfyz, bo.getSfyz());
        lqw.eq(StringUtils.isNotBlank(bo.getKslx()), W2Queuhis::getKslx, bo.getKslx());
        lqw.eq(bo.getDjc() != null, W2Queuhis::getDjc, bo.getDjc());
        lqw.eq(StringUtils.isNotBlank(bo.getJxdm()), W2Queuhis::getJxdm, bo.getJxdm());
        lqw.eq(bo.getKsrq() != null, W2Queuhis::getKsrq, bo.getKsrq());
        lqw.eq(StringUtils.isNotBlank(bo.getKsxm()), W2Queuhis::getKsxm, bo.getKsxm());
        lqw.eq(StringUtils.isNotBlank(bo.getZt()), W2Queuhis::getZt, bo.getZt());
        lqw.eq(bo.getSqsj() != null, W2Queuhis::getSqsj, bo.getSqsj());
        lqw.eq(StringUtils.isNotBlank(bo.getJxmc()), W2Queuhis::getJxmc, bo.getJxmc());
        lqw.eq(StringUtils.isNotBlank(bo.getQxlx()), W2Queuhis::getQxlx, bo.getQxlx());
        lqw.eq(StringUtils.isNotBlank(bo.getKcxh()), W2Queuhis::getKcxh, bo.getKcxh());
        lqw.eq(StringUtils.isNotBlank(bo.getFlag()), W2Queuhis::getFlag, bo.getFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getJszt()), W2Queuhis::getJszt, bo.getJszt());
        lqw.eq(StringUtils.isNotBlank(bo.getKg()), W2Queuhis::getKg, bo.getKg());
        lqw.eq(StringUtils.isNotBlank(bo.getLsh()), W2Queuhis::getLsh, bo.getLsh());
        lqw.eq(StringUtils.isNotBlank(bo.getZkxmdm()), W2Queuhis::getZkxmdm, bo.getZkxmdm());
        lqw.like(StringUtils.isNotBlank(bo.getKgname()), W2Queuhis::getKgname, bo.getKgname());
        lqw.eq(StringUtils.isNotBlank(bo.getFinger()), W2Queuhis::getFinger, bo.getFinger());
        lqw.eq(StringUtils.isNotBlank(bo.getSqks()), W2Queuhis::getSqks, bo.getSqks());
        lqw.eq(StringUtils.isNotBlank(bo.getKchp()), W2Queuhis::getKchp, bo.getKchp());
        lqw.eq(StringUtils.isNotBlank(bo.getSfyk()), W2Queuhis::getSfyk, bo.getSfyk());
        lqw.eq(StringUtils.isNotBlank(bo.getZsfhg()), W2Queuhis::getZsfhg, bo.getZsfhg());
        lqw.eq(bo.getZcs() != null, W2Queuhis::getZcs, bo.getZcs());
        lqw.eq(bo.getScore() != null, W2Queuhis::getScore, bo.getScore());
        lqw.eq(StringUtils.isNotBlank(bo.getKfxm()), W2Queuhis::getKfxm, bo.getKfxm());
        lqw.eq(StringUtils.isNotBlank(bo.getWcxm()), W2Queuhis::getWcxm, bo.getWcxm());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbs()), W2Queuhis::getKcbs, bo.getKcbs());
        lqw.eq(StringUtils.isNotBlank(bo.getFieldid()), W2Queuhis::getFieldid, bo.getFieldid());
        lqw.eq(StringUtils.isNotBlank(bo.getKg2()), W2Queuhis::getKg2, bo.getKg2());
        lqw.eq(bo.getKscc() != null, W2Queuhis::getKscc, bo.getKscc());
        lqw.eq(bo.getSign() != null, W2Queuhis::getSign, bo.getSign());
        lqw.eq(StringUtils.isNotBlank(bo.getSigncontent()), W2Queuhis::getSigncontent, bo.getSigncontent());
        lqw.eq(bo.getRLine() != null, W2Queuhis::getRLine, bo.getRLine());
        lqw.eq(StringUtils.isNotBlank(bo.getKsyy()), W2Queuhis::getKsyy, bo.getKsyy());
        lqw.eq(StringUtils.isNotBlank(bo.getJbr()), W2Queuhis::getJbr, bo.getJbr());
        lqw.eq(StringUtils.isNotBlank(bo.getGlbm()), W2Queuhis::getGlbm, bo.getGlbm());
        lqw.eq(StringUtils.isNotBlank(bo.getYycs()), W2Queuhis::getYycs, bo.getYycs());
        lqw.eq(StringUtils.isNotBlank(bo.getBcyykscs()), W2Queuhis::getBcyykscs, bo.getBcyykscs());
        lqw.eq(StringUtils.isNotBlank(bo.getSSafe()), W2Queuhis::getSSafe, bo.getSSafe());
        lqw.eq(StringUtils.isNotBlank(bo.getSSafeZjhm()), W2Queuhis::getSSafeZjhm, bo.getSSafeZjhm());
        lqw.eq(bo.getILock() != null, W2Queuhis::getILock, bo.getILock());
        lqw.eq(StringUtils.isNotBlank(bo.getLinecode()), W2Queuhis::getLinecode, bo.getLinecode());
        lqw.eq(bo.getXmkssj() != null, W2Queuhis::getXmkssj, bo.getXmkssj());
        lqw.eq(StringUtils.isNotBlank(bo.getMessage()), W2Queuhis::getMessage, bo.getMessage());
        lqw.eq(bo.getIVoiceTimes() != null, W2Queuhis::getIVoiceTimes, bo.getIVoiceTimes());
        lqw.eq(bo.getDVoiceDate() != null, W2Queuhis::getDVoiceDate, bo.getDVoiceDate());
        lqw.eq(bo.getIVoiceReady() != null, W2Queuhis::getIVoiceReady, bo.getIVoiceReady());
        lqw.eq(bo.getCwcs() != null, W2Queuhis::getCwcs, bo.getCwcs());
        lqw.eq(StringUtils.isNotBlank(bo.getKskm()), W2Queuhis::getKskm, bo.getKskm());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(W2QueuhisBo bo) {
        W2Queuhis add = BeanUtil.toBean(bo, W2Queuhis.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(W2QueuhisBo bo) {
        W2Queuhis update = BeanUtil.toBean(bo, W2Queuhis.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Queuhis entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
