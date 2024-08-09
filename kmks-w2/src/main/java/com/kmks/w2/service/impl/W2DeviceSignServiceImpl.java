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
import com.kmks.w2.domain.bo.W2DeviceSignBo;
import com.kmks.w2.domain.vo.W2DeviceSignVo;
import com.kmks.w2.domain.W2DeviceSign;
import com.kmks.w2.mapper.W2DeviceSignMapper;
import com.kmks.w2.service.IW2DeviceSignService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 签到设备Service业务层处理
 *
 * @author lynn
 * @date 2024-04-17
 */
@RequiredArgsConstructor
@Service
public class W2DeviceSignServiceImpl implements IW2DeviceSignService {

    private final W2DeviceSignMapper baseMapper;

    /**
     * 查询签到设备
     */
    @Override
    public W2DeviceSignVo queryById(Long signId){
        return baseMapper.selectVoById(signId);
    }

    /**
     * 查询签到设备列表
     */
    @Override
    public TableDataInfo<W2DeviceSignVo> queryPageList(W2DeviceSignBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2DeviceSign> lqw = buildQueryWrapper(bo);
        Page<W2DeviceSignVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询签到设备列表
     */
    @Override
    public List<W2DeviceSignVo> queryList(W2DeviceSignBo bo) {
        LambdaQueryWrapper<W2DeviceSign> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2DeviceSign> buildQueryWrapper(W2DeviceSignBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2DeviceSign> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSignId() != null, W2DeviceSign::getSignId, bo.getSignId());
        lqw.like(bo.getSignName() != null, W2DeviceSign::getSignName, bo.getSignName());
        lqw.eq(StringUtils.isNotBlank(bo.getSignStatus()), W2DeviceSign::getSignStatus, bo.getSignStatus());
        return lqw;
    }

    /**
     * 新增签到设备
     */
    @Override
    public Boolean insertByBo(W2DeviceSignBo bo) {
        W2DeviceSign add = BeanUtil.toBean(bo, W2DeviceSign.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setSignId(add.getSignId());
        }
        return flag;
    }

    /**
     * 修改签到设备
     */
    @Override
    public Boolean updateByBo(W2DeviceSignBo bo) {
        W2DeviceSign update = BeanUtil.toBean(bo, W2DeviceSign.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2DeviceSign entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除签到设备
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
