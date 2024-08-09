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
import com.kmks.w2.domain.bo.W2DeviceGateBo;
import com.kmks.w2.domain.vo.W2DeviceGateVo;
import com.kmks.w2.domain.W2DeviceGate;
import com.kmks.w2.mapper.W2DeviceGateMapper;
import com.kmks.w2.service.IW2DeviceGateService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 闸机设备Service业务层处理
 *
 * @author Lynn
 * @date 2024-04-17
 */
@RequiredArgsConstructor
@Service
public class W2DeviceGateServiceImpl implements IW2DeviceGateService {

    private final W2DeviceGateMapper baseMapper;

    /**
     * 查询闸机设备
     */
    @Override
    public W2DeviceGateVo queryById(Long gateId){
        return baseMapper.selectVoById(gateId);
    }

    /**
     * 查询闸机设备列表
     */
    @Override
    public TableDataInfo<W2DeviceGateVo> queryPageList(W2DeviceGateBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2DeviceGate> lqw = buildQueryWrapper(bo);
        Page<W2DeviceGateVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询闸机设备列表
     */
    @Override
    public List<W2DeviceGateVo> queryList(W2DeviceGateBo bo) {
        LambdaQueryWrapper<W2DeviceGate> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2DeviceGate> buildQueryWrapper(W2DeviceGateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2DeviceGate> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getGateId() != null, W2DeviceGate::getGateId, bo.getGateId());
        lqw.like(bo.getGateName() != null, W2DeviceGate::getGateName, bo.getGateName());
        lqw.eq(StringUtils.isNotBlank(bo.getGateIp()), W2DeviceGate::getGateIp, bo.getGateIp());
        lqw.eq(StringUtils.isNotBlank(bo.getGateMac()), W2DeviceGate::getGateMac, bo.getGateMac());
        lqw.eq(StringUtils.isNotBlank(bo.getGateStatus()), W2DeviceGate::getGateStatus, bo.getGateStatus());
        return lqw;
    }

    /**
     * 新增闸机设备
     */
    @Override
    public Boolean insertByBo(W2DeviceGateBo bo) {
        W2DeviceGate add = BeanUtil.toBean(bo, W2DeviceGate.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setGateId(add.getGateId());
        }
        return flag;
    }

    /**
     * 修改闸机设备
     */
    @Override
    public Boolean updateByBo(W2DeviceGateBo bo) {
        W2DeviceGate update = BeanUtil.toBean(bo, W2DeviceGate.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2DeviceGate entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除闸机设备
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
