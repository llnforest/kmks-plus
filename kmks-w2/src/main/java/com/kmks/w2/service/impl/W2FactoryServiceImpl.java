package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2Factory;
import com.kmks.w2.domain.bo.W2FactoryBo;
import com.kmks.w2.domain.vo.W2FactoryVo;
import com.kmks.w2.mapper.W2FactoryMapper;
import com.kmks.w2.service.IW2FactoryService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 考点信息Service业务层处理
 *
 * @author lynn
 * @date 2023-04-28
 */
@RequiredArgsConstructor
@Service
public class W2FactoryServiceImpl implements IW2FactoryService {

    private final W2FactoryMapper baseMapper;

    /**
     * 查询考点信息
     */
    @Override
    public W2FactoryVo queryById(String xh){
        return baseMapper.selectVoById(xh);
    }

    /**
     * 查询考点信息列表
     */
    @Override
    public TableDataInfo<W2FactoryVo> queryPageList(W2FactoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Factory> lqw = buildQueryWrapper(bo);
        Page<W2FactoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询考点信息列表
     */
    @Override
    public List<W2FactoryVo> queryList(W2FactoryBo bo) {
        LambdaQueryWrapper<W2Factory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Factory> buildQueryWrapper(W2FactoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Factory> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增考点信息
     */
    @Override
    public Boolean insertByBo(W2FactoryBo bo) {
        W2Factory add = BeanUtil.toBean(bo, W2Factory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setXh(add.getXh());
        }
        return flag;
    }

    /**
     * 修改考点信息
     */
    @Override
    public Boolean updateByBo(W2FactoryBo bo) {
        W2Factory update = BeanUtil.toBean(bo, W2Factory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Factory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除考点信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
