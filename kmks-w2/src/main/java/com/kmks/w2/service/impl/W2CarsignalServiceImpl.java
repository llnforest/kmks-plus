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
import com.kmks.w2.domain.bo.W2CarsignalBo;
import com.kmks.w2.domain.vo.W2CarsignalVo;
import com.kmks.w2.domain.W2Carsignal;
import com.kmks.w2.mapper.W2CarsignalMapper;
import com.kmks.w2.service.IW2CarsignalService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 车辆信号Service业务层处理
 *
 * @author Lynn
 * @date 2024-05-29
 */
@RequiredArgsConstructor
@Service
public class W2CarsignalServiceImpl implements IW2CarsignalService {

    private final W2CarsignalMapper baseMapper;

    /**
     * 查询车辆信号
     */
    @Override
    public W2CarsignalVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询车辆信号列表
     */
    @Override
    public TableDataInfo<W2CarsignalVo> queryPageList(W2CarsignalBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Carsignal> lqw = buildQueryWrapper(bo);
        Page<W2CarsignalVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车辆信号列表
     */
    @Override
    public List<W2CarsignalVo> queryList(W2CarsignalBo bo) {
        LambdaQueryWrapper<W2Carsignal> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Carsignal> buildQueryWrapper(W2CarsignalBo bo) {
        LambdaQueryWrapper<W2Carsignal> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSignalKey()), W2Carsignal::getSignalKey, bo.getSignalKey());
        lqw.eq(StringUtils.isNotBlank(bo.getSignalValue()), W2Carsignal::getSignalValue, bo.getSignalValue());
        lqw.like(StringUtils.isNotBlank(bo.getSignalName()), W2Carsignal::getSignalName, bo.getSignalName());
        lqw.eq(StringUtils.isNotBlank(bo.getSignalNote()), W2Carsignal::getSignalNote, bo.getSignalNote());
        return lqw;
    }

    /**
     * 新增车辆信号
     */
    @Override
    public Boolean insertByBo(W2CarsignalBo bo) {
        W2Carsignal add = BeanUtil.toBean(bo, W2Carsignal.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改车辆信号
     */
    @Override
    public Boolean updateByBo(W2CarsignalBo bo) {
        W2Carsignal update = BeanUtil.toBean(bo, W2Carsignal.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Carsignal entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车辆信号
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
