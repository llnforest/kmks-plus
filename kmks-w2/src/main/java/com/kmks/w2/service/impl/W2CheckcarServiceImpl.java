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
import com.kmks.w2.domain.bo.W2CheckcarBo;
import com.kmks.w2.domain.vo.W2CheckcarVo;
import com.kmks.w2.domain.W2Checkcar;
import com.kmks.w2.mapper.W2CheckcarMapper;
import com.kmks.w2.service.IW2CheckcarService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 车辆自检Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class W2CheckcarServiceImpl implements IW2CheckcarService {

    private final W2CheckcarMapper baseMapper;

    /**
     * 查询车辆自检
     */
    @Override
    public W2CheckcarVo queryById(String sCarno){
        return baseMapper.selectVoById(sCarno);
    }

    /**
     * 查询车辆自检列表
     */
    @Override
    public TableDataInfo<W2CheckcarVo> queryPageList(W2CheckcarBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Checkcar> lqw = buildQueryWrapper(bo);
        Page<W2CheckcarVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车辆自检列表
     */
    @Override
    public List<W2CheckcarVo> queryList(W2CheckcarBo bo) {
        LambdaQueryWrapper<W2Checkcar> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Checkcar> buildQueryWrapper(W2CheckcarBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Checkcar> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSCarno()), W2Checkcar::getSCarno, bo.getSCarno());
        lqw.eq(StringUtils.isNotBlank(bo.getSTime()), W2Checkcar::getSTime, bo.getSTime());
        lqw.eq(StringUtils.isNotBlank(bo.getSValue()), W2Checkcar::getSValue, bo.getSValue());
        lqw.eq(StringUtils.isNotBlank(bo.getSNote()), W2Checkcar::getSNote, bo.getSNote());
        lqw.eq(bo.getDtFs() != null, W2Checkcar::getDtFs, bo.getDtFs());
        return lqw;
    }

    /**
     * 新增车辆自检
     */
    @Override
    public Boolean insertByBo(W2CheckcarBo bo) {
        W2Checkcar add = BeanUtil.toBean(bo, W2Checkcar.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setSCarno(add.getSCarno());
        }
        return flag;
    }

    /**
     * 修改车辆自检
     */
    @Override
    public Boolean updateByBo(W2CheckcarBo bo) {
        W2Checkcar update = BeanUtil.toBean(bo, W2Checkcar.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Checkcar entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车辆自检
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
