package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.W2Kfconfig;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2KsxmkfdmJgBo;
import com.kmks.w2.domain.vo.W2KsxmkfdmJgVo;
import com.kmks.w2.domain.W2KsxmkfdmJg;
import com.kmks.w2.mapper.W2KsxmkfdmJgMapper;
import com.kmks.w2.service.IW2KsxmkfdmJgService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 扣分代码Service业务层处理
 *
 * @author lynn
 * @date 2024-04-23
 */
@RequiredArgsConstructor
@Service
public class W2KsxmkfdmJgServiceImpl implements IW2KsxmkfdmJgService {

    private final W2KsxmkfdmJgMapper baseMapper;

    private final ISysConfigService configService;

    /**
     * 查询扣分代码
     */
    @Override
    public W2KsxmkfdmJgVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询扣分代码列表
     */
    @Override
    public TableDataInfo<W2KsxmkfdmJgVo> queryPageList(W2KsxmkfdmJgBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2KsxmkfdmJg> lqw = buildQueryWrapper(bo);
        Page<W2KsxmkfdmJgVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询扣分代码列表
     */
    @Override
    public List<W2KsxmkfdmJgVo> queryList(W2KsxmkfdmJgBo bo) {
        LambdaQueryWrapper<W2KsxmkfdmJg> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2KsxmkfdmJg> buildQueryWrapper(W2KsxmkfdmJgBo bo) {
        LambdaQueryWrapper<W2KsxmkfdmJg> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getKsxmdm() != null, W2KsxmkfdmJg::getKsxmdm, bo.getKsxmdm());
        lqw.eq(StringUtils.isNotBlank(bo.getGakfdm()), W2KsxmkfdmJg::getGakfdm, bo.getGakfdm());
        lqw.like(StringUtils.isNotBlank(bo.getKfmc()), W2KsxmkfdmJg::getKfmc, bo.getKfmc());
        lqw.eq(StringUtils.isNotBlank(bo.getKskm()), W2KsxmkfdmJg::getKskm, bo.getKskm());
        lqw.eq(StringUtils.isNotBlank(bo.getKsxm()), W2KsxmkfdmJg::getKsxm, bo.getKsxm());
        lqw.eq(bo.getFlag() != null, W2KsxmkfdmJg::getFlag, bo.getFlag());
        lqw.in(W2KsxmkfdmJg::getKskm,configService.selectConfigByKey(CacheNames.COURSE_KEY).split(","));
        lqw.orderByDesc(W2KsxmkfdmJg::getId);
        return lqw;
    }

    /**
     * 新增扣分代码
     */
    @Override
    public Boolean insertByBo(W2KsxmkfdmJgBo bo) {
        W2KsxmkfdmJg add = BeanUtil.toBean(bo, W2KsxmkfdmJg.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改扣分代码
     */
    @Override
    public Boolean updateByBo(W2KsxmkfdmJgBo bo) {
        W2KsxmkfdmJg update = BeanUtil.toBean(bo, W2KsxmkfdmJg.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2KsxmkfdmJg entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除扣分代码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
