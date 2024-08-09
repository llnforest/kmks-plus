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
import com.kmks.w2.domain.bo.W2KcxxCheckBo;
import com.kmks.w2.domain.vo.W2KcxxCheckVo;
import com.kmks.w2.domain.W2KcxxCheck;
import com.kmks.w2.mapper.W2KcxxCheckMapper;
import com.kmks.w2.service.IW2KcxxCheckService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 考车自检Service业务层处理
 *
 * @author Lynn
 * @date 2024-06-19
 */
@RequiredArgsConstructor
@Service
public class W2KcxxCheckServiceImpl implements IW2KcxxCheckService {

    private final W2KcxxCheckMapper baseMapper;

    /**
     * 查询考车自检
     */
    @Override
    public W2KcxxCheckVo queryById(String kcbh){
        return baseMapper.selectVoById(kcbh);
    }

    /**
     * 查询考车自检列表
     */
    @Override
    public TableDataInfo<W2KcxxCheckVo> queryPageList(W2KcxxCheckBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2KcxxCheck> lqw = buildQueryWrapper(bo);
        Page<W2KcxxCheckVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询考车自检列表
     */
    @Override
    public List<W2KcxxCheckVo> queryList(W2KcxxCheckBo bo) {
        LambdaQueryWrapper<W2KcxxCheck> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2KcxxCheck> buildQueryWrapper(W2KcxxCheckBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2KcxxCheck> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2KcxxCheck::getKcbh, bo.getKcbh());
        lqw.eq(bo.getCheckResult() != null, W2KcxxCheck::getCheckResult, bo.getCheckResult());
        lqw.like(StringUtils.isNotBlank(bo.getCheckContent()), W2KcxxCheck::getCheckContent, bo.getCheckContent());
        lqw.between(params.get("beginCheckTime") != null && params.get("endCheckTime") != null,
            W2KcxxCheck::getCheckTime ,params.get("beginCheckTime"), params.get("endCheckTime"));
        lqw.orderByDesc(W2KcxxCheck::getCheckTime);
        return lqw;
    }

    /**
     * 新增考车自检
     */
    @Override
    public Boolean insertByBo(W2KcxxCheckBo bo) {
        W2KcxxCheck add = BeanUtil.toBean(bo, W2KcxxCheck.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setKcbh(add.getKcbh());
        }
        return flag;
    }

    /**
     * 修改考车自检
     */
    @Override
    public Boolean updateByBo(W2KcxxCheckBo bo) {
        W2KcxxCheck update = BeanUtil.toBean(bo, W2KcxxCheck.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2KcxxCheck entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除考车自检
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
