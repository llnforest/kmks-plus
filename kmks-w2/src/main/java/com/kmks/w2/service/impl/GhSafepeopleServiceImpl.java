package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.GhSafepeople;
import com.kmks.w2.domain.bo.GhSafepeopleBo;
import com.kmks.w2.domain.vo.GhSafepeopleVo;
import com.kmks.w2.mapper.GhSafepeopleMapper;
import com.kmks.w2.service.IGhSafepeopleService;
import com.ruoyi.common.utils.StringUtils;
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
 * 安全员管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class GhSafepeopleServiceImpl implements IGhSafepeopleService {

    private final GhSafepeopleMapper baseMapper;

    /**
     * 查询安全员管理
     */
    @Override
    public GhSafepeopleVo queryById(String sZjhm){
        return baseMapper.selectVoById(sZjhm);
    }

    /**
     * 查询安全员管理列表
     */
    @Override
    public TableDataInfo<GhSafepeopleVo> queryPageList(GhSafepeopleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<GhSafepeople> lqw = buildQueryWrapper(bo);
        Page<GhSafepeopleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询安全员管理列表
     */
    @Override
    public List<GhSafepeopleVo> queryList(GhSafepeopleBo bo) {
        LambdaQueryWrapper<GhSafepeople> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<GhSafepeople> buildQueryWrapper(GhSafepeopleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<GhSafepeople> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getSName()), GhSafepeople::getSName, bo.getSName());
        return lqw;
    }

    /**
     * 新增安全员管理
     */
    @Override
    public Boolean insertByBo(GhSafepeopleBo bo) {
        GhSafepeople add = BeanUtil.toBean(bo, GhSafepeople.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setSZjhm(add.getSZjhm());
        }
        return flag;
    }

    /**
     * 修改安全员管理
     */
    @Override
    public Boolean updateByBo(GhSafepeopleBo bo) {
        GhSafepeople update = BeanUtil.toBean(bo, GhSafepeople.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(GhSafepeople entity){
        //TODO 做一些数据校验,如唯一约束

    }

    /**
     * 批量删除安全员管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
