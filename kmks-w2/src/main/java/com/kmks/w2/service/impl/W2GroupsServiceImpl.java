package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2Groups;
import com.kmks.w2.domain.bo.W2GroupsBo;
import com.kmks.w2.domain.vo.W2GroupsVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.mapper.W2GroupsMapper;
import com.kmks.w2.service.IW2GroupsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 明细分组信息Service业务层处理
 *
 * @author lynn
 * @date 2023-04-28
 */
@RequiredArgsConstructor
@Service
public class W2GroupsServiceImpl implements IW2GroupsService {

    private final W2GroupsMapper baseMapper;

    /**
     * 查询明细分组信息
     */
    @Override
    public W2GroupsVo queryById(String xh){
        return baseMapper.selectVoById(xh);
    }

    /**
     * 查询明细分组信息列表
     */
    @Override
    public TableDataInfo<W2GroupsVo> queryPageList(W2GroupsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Groups> lqw = buildQueryWrapper(bo);
        Page<W2GroupsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询明细分组信息列表
     */
    @Override
    public List<W2GroupsVo> queryList(W2GroupsBo bo) {
        LambdaQueryWrapper<W2Groups> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Groups> buildQueryWrapper(W2GroupsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Groups> lqw = Wrappers.lambdaQuery();
        lqw.between(params.get("beginKsrq") != null && params.get("endKsrq") != null,
            W2Groups::getKsrq ,params.get("beginKsrq"), params.get("endKsrq"));
        return lqw;
    }

    /**
     * 新增明细分组信息
     */
    @Override
    public Boolean insertByBo(W2GroupsBo bo) {
        W2Groups add = BeanUtil.toBean(bo, W2Groups.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setXh(add.getXh());
        }
        return flag;
    }

    /**
     * 修改明细分组信息
     */
    @Override
    public Boolean updateByBo(W2GroupsBo bo) {
        W2Groups update = BeanUtil.toBean(bo, W2Groups.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Groups entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除明细分组信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
