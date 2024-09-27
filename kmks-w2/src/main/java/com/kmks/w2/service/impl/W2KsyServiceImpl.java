package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.kmks.jianguanold.domain.vo.A17C04Vo;
import com.kmks.w2.domain.bo.W2KsyBo;
import com.kmks.w2.domain.vo.W2KsyVo;
import com.kmks.w2.mapper.W2KsyMapper;
import com.kmks.w2.service.IW2KsyService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.W2Ksy;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 考试员信息Service业务层处理
 *
 * @author lynn
 * @date 2023-04-28
 */
@RequiredArgsConstructor
@Service
public class W2KsyServiceImpl implements IW2KsyService {

    private final W2KsyMapper baseMapper;

    /**
     * 查询考试员信息
     */
    @Override
    public W2KsyVo queryById(String xh) {
        return baseMapper.selectVoById(xh);
    }

    /**
     * 查询考试员信息列表
     */
    @Override
    public TableDataInfo<W2KsyVo> queryPageList(W2KsyBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Ksy> lqw = buildQueryWrapper(bo);
        Page<W2KsyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询考试员信息列表
     */
    @Override
    public List<W2KsyVo> queryList(W2KsyBo bo) {
        LambdaQueryWrapper<W2Ksy> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Ksy> buildQueryWrapper(W2KsyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Ksy> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSszd()), W2Ksy::getSszd, bo.getSszd());
        lqw.eq(StringUtils.isNotBlank(bo.getGlbm()), W2Ksy::getGlbm, bo.getGlbm());
        lqw.eq(StringUtils.isNotBlank(bo.getSfzmmc()), W2Ksy::getSfzmmc, bo.getSfzmmc());
        lqw.eq(StringUtils.isNotBlank(bo.getSfzmhm()), W2Ksy::getSfzmhm, bo.getSfzmhm());
        lqw.eq(StringUtils.isNotBlank(bo.getDabh()), W2Ksy::getDabh, bo.getDabh());
        lqw.eq(StringUtils.isNotBlank(bo.getXm()), W2Ksy::getXm, bo.getXm());
        lqw.eq(StringUtils.isNotBlank(bo.getXb()), W2Ksy::getXb, bo.getXb());
        lqw.eq(StringUtils.isNotBlank(bo.getCsrq()), W2Ksy::getCsrq, bo.getCsrq());
        lqw.eq(StringUtils.isNotBlank(bo.getZkcx()), W2Ksy::getZkcx, bo.getZkcx());
        lqw.eq(StringUtils.isNotBlank(bo.getFzrq()), W2Ksy::getFzrq, bo.getFzrq());
        lqw.eq(StringUtils.isNotBlank(bo.getKszyxqz()), W2Ksy::getKszyxqz, bo.getKszyxqz());
        lqw.eq(StringUtils.isNotBlank(bo.getKsyzt()), W2Ksy::getKsyzt, bo.getKsyzt());
        lqw.eq(StringUtils.isNotBlank(bo.getGzdw()), W2Ksy::getGzdw, bo.getGzdw());
        lqw.eq(StringUtils.isNotBlank(bo.getJbr()), W2Ksy::getJbr, bo.getJbr());
        lqw.eq(StringUtils.isNotBlank(bo.getFzjg()), W2Ksy::getFzjg, bo.getFzjg());
        lqw.eq(StringUtils.isNotBlank(bo.getCjsj()), W2Ksy::getCjsj, bo.getCjsj());
        lqw.eq(StringUtils.isNotBlank(bo.getGxsj()), W2Ksy::getGxsj, bo.getGxsj());
        lqw.orderByDesc(W2Ksy::getXh);
        return lqw;
    }

    /**
     * 新增考试员信息
     */
    @Override
    public Boolean insertByBo(W2KsyBo bo) {
        bo.setCjsj(DateUtil.now());
        bo.setGxsj(DateUtil.now());
        W2Ksy add = BeanUtil.toBean(bo, W2Ksy.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setXh(add.getXh());
        }
        return flag;
    }

    /**
     * 修改考试员信息
     */
    @Override
    public Boolean updateByBo(W2KsyBo bo) {
        bo.setGxsj(DateUtil.now());
        W2Ksy update = BeanUtil.toBean(bo, W2Ksy.class);

        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Ksy entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除考试员信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 下载ksy信息
     *
     * @param list 列表
     * @return {@link Boolean}
     */
    @Override
    public Boolean downLoadKsy(List<A17C04Vo.Body> list) {
        baseMapper.delete(null);
        List<W2Ksy> collect = list.stream().map(v -> BeanUtil.toBean(v, W2Ksy.class)).collect(Collectors.toList());
        return baseMapper.insertBatch(collect);
    }
}
