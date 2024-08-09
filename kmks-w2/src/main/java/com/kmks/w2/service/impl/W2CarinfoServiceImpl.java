package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2Carinfo;
import com.kmks.w2.service.IW2CarinfoService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2CarinfoBo;
import com.kmks.w2.domain.vo.W2CarinfoVo;
import com.kmks.w2.mapper.W2CarinfoMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 车辆信息Service业务层处理
 *
 * @author lynn
 * @date 2023-04-28
 */
@RequiredArgsConstructor
@Service
public class W2CarinfoServiceImpl implements IW2CarinfoService {

    private final W2CarinfoMapper baseMapper;

    /**
     * 查询车辆信息
     */
    @Override
    public W2CarinfoVo queryById(String xh){
        return baseMapper.selectVoById(xh);
    }

    /**
     * 查询车辆信息列表
     */
    @Override
    public TableDataInfo<W2CarinfoVo> queryPageList(W2CarinfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Carinfo> lqw = buildQueryWrapper(bo);
        Page<W2CarinfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车辆信息列表
     */
    @Override
    public List<W2CarinfoVo> queryList(W2CarinfoBo bo) {
        LambdaQueryWrapper<W2Carinfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Carinfo> buildQueryWrapper(W2CarinfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Carinfo> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增车辆信息
     */
    @Override
    public Boolean insertByBo(W2CarinfoBo bo) {
        W2Carinfo add = BeanUtil.toBean(bo, W2Carinfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setXh(add.getXh());
        }
        return flag;
    }

    /**
     * 修改车辆信息
     */
    @Override
    public Boolean updateByBo(W2CarinfoBo bo) {
        W2Carinfo update = BeanUtil.toBean(bo, W2Carinfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Carinfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车辆信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
