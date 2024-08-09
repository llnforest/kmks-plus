package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2ConfigDevice;
import com.kmks.w2.domain.bo.W2ConfigDeviceBo;
import com.kmks.w2.domain.vo.W2ConfigDeviceVo;
import com.kmks.w2.mapper.W2ConfigDeviceMapper;
import com.kmks.w2.service.IW2ConfigDeviceService;
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
import java.util.stream.Collectors;

/**
 * 设备合码器Service业务层处理
 *
 * @author lynn
 * @date 2023-05-04
 */
@RequiredArgsConstructor
@Service
public class W2ConfigDeviceServiceImpl implements IW2ConfigDeviceService {

    private final W2ConfigDeviceMapper baseMapper;

    /**
     * 查询设备合码器
     */
    @Override
    public W2ConfigDeviceVo queryById(Long deviceno){
        return baseMapper.selectVoById(deviceno);
    }

    /**
     * 查询设备合码器列表
     */
    @Override
    public TableDataInfo<W2ConfigDeviceVo> queryPageList(W2ConfigDeviceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2ConfigDevice> lqw = buildQueryWrapper(bo);
        Page<W2ConfigDeviceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备合码器列表
     */
    @Override
    public List<W2ConfigDeviceVo> queryList(W2ConfigDeviceBo bo) {
        LambdaQueryWrapper<W2ConfigDevice> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2ConfigDevice> buildQueryWrapper(W2ConfigDeviceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2ConfigDevice> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增设备合码器
     */
    @Override
    public Boolean insertByBo(W2ConfigDeviceBo bo) {
        W2ConfigDevice add = BeanUtil.toBean(bo, W2ConfigDevice.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeviceno(add.getDeviceno());
        }
        return flag;
    }

    /**
     * 修改设备合码器
     */
    @Override
    public Boolean updateByBo(W2ConfigDeviceBo bo) {
        W2ConfigDevice update = BeanUtil.toBean(bo, W2ConfigDevice.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2ConfigDevice entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备合码器
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 新增车辆合码器
     */
    @Override
    public Boolean insertBatch(Collection<W2ConfigDeviceVo> collection) {
        List<W2ConfigDevice> collect = collection.stream().map(v -> BeanUtil.toBean(v, W2ConfigDevice.class)).collect(Collectors.toList());
        return baseMapper.insertBatch(collect);
    }
}
