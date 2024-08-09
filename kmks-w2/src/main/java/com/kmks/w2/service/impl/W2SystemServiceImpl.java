package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2System;
import com.kmks.w2.domain.bo.W2SystemBo;
import com.kmks.w2.mapper.W2SystemMapper;
import com.kmks.w2.service.IW2SystemService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.vo.W2SystemVo;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 基础编码Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-27
 */
@RequiredArgsConstructor
@Service
public class W2SystemServiceImpl implements IW2SystemService {

    private final W2SystemMapper baseMapper;

    /**
     * 查询基础编码
     */
    @Override
    public W2SystemVo queryById(Long nid){
        return baseMapper.selectVoByIdAndType(nid);
    }

    @Override
    public W2SystemVo selectVoByIdAndType(Long nid) {
        return baseMapper.selectVoByIdAndType(nid);
    }

    /**
     * 查询基础编码列表
     */
    @Override
    public TableDataInfo<W2SystemVo> queryPageList(W2SystemBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2System> lqw = buildQueryWrapper(bo);
        Page<W2SystemVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询基础编码列表
     */
    @Override
    public List<W2SystemVo> queryList(W2SystemBo bo) {
        LambdaQueryWrapper<W2System> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2System> buildQueryWrapper(W2SystemBo bo) {
        LambdaQueryWrapper<W2System> lqw = Wrappers.lambdaQuery();
        lqw.eq(W2System::getType,"3");
        lqw.eq(bo.getNid() != null, W2System::getNid, bo.getNid());
        lqw.eq(StringUtils.isNotBlank(bo.getParamdm()), W2System::getParamdm, bo.getParamdm());
        lqw.like(StringUtils.isNotBlank(bo.getParamname()), W2System::getParamname, bo.getParamname());
        lqw.eq(StringUtils.isNotBlank(bo.getBz()), W2System::getBz, bo.getBz());
        lqw.eq(StringUtils.isNotBlank(bo.getParamtype()), W2System::getParamtype, bo.getParamtype());
        lqw.orderByDesc(W2System::getNid);
        return lqw;
    }

    /**
     * 新增基础编码
     */
    @Override
    public Boolean insertByBo(W2SystemBo bo) {
        W2System add = BeanUtil.toBean(bo, W2System.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setNid(add.getNid());
        }
        return flag;
    }

    /**
     * 修改基础编码
     */
    @Override
    public Boolean updateByBo(W2SystemBo bo) {
        W2System update = BeanUtil.toBean(bo, W2System.class);
        validEntityBeforeSave(update);
        return baseMapper.updateVoByIdAndType(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2System entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除基础编码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
