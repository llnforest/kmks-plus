package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2GroupsTemp;
import com.kmks.w2.domain.bo.W2GroupsTempBo;
import com.kmks.w2.domain.vo.W2GroupsTempVo;
import com.kmks.w2.mapper.W2GroupsTempMapper;
import com.kmks.w2.service.IW2GroupsTempService;
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
 * 查询下载信息Service业务层处理
 *
 * @author lynn
 * @date 2023-04-28
 */
@RequiredArgsConstructor
@Service
public class W2GroupsTempServiceImpl implements IW2GroupsTempService {

    private final W2GroupsTempMapper baseMapper;

    /**
     * 查询查询下载信息
     */
    @Override
    public W2GroupsTempVo queryById(String lsh){
        return baseMapper.selectVoById(lsh);
    }

    /**
     * 查询查询下载信息列表
     */
    @Override
    public TableDataInfo<W2GroupsTempVo> queryPageList(W2GroupsTempBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2GroupsTemp> lqw = buildQueryWrapper(bo);
        Page<W2GroupsTempVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询查询下载信息列表
     */
    @Override
    public List<W2GroupsTempVo> queryList(W2GroupsTempBo bo) {
        LambdaQueryWrapper<W2GroupsTemp> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2GroupsTemp> buildQueryWrapper(W2GroupsTempBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2GroupsTemp> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getZjhm()), W2GroupsTemp::getZjhm, bo.getZjhm());
        lqw.like(StringUtils.isNotBlank(bo.getXm()), W2GroupsTemp::getXm, bo.getXm());
        lqw.like(StringUtils.isNotBlank(bo.getZkzmbh()), W2GroupsTemp::getZkzmbh, bo.getZkzmbh());
        lqw.between(params.get("beginKsrq") != null && params.get("endKsrq") != null,
            W2GroupsTemp::getKsrq ,params.get("beginKsrq"), params.get("endKsrq"));
        return lqw;
    }

    /**
     * 新增查询下载信息
     */
    @Override
    public Boolean insertByBo(W2GroupsTempBo bo) {
        W2GroupsTemp add = BeanUtil.toBean(bo, W2GroupsTemp.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLsh(add.getLsh());
        }
        return flag;
    }

    /**
     * 修改查询下载信息
     */
    @Override
    public Boolean updateByBo(W2GroupsTempBo bo) {
        W2GroupsTemp update = BeanUtil.toBean(bo, W2GroupsTemp.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2GroupsTemp entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除查询下载信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
