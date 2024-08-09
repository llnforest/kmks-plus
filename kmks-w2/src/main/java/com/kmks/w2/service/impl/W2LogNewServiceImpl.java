package com.kmks.w2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.w2.domain.W2LogNew;
import com.kmks.w2.domain.vo.W2LogNewVo;
import com.kmks.w2.mapper.W2LogNewMapper;
import com.kmks.w2.service.IW2LogNewService;
import com.kmks.w2.domain.bo.W2LogNewBo;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 项目代码Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class W2LogNewServiceImpl implements IW2LogNewService {

    private final W2LogNewMapper baseMapper;

    @Override
    public W2LogNewVo queryById(Long lIncode) {
        return baseMapper.selectVoById(lIncode);
    }

    @Override
    public TableDataInfo<W2LogNewVo> queryPageList(W2LogNewBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2LogNew> lqw = buildQueryWrapper(bo);
        Page<W2LogNewVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<W2LogNew> buildQueryWrapper(W2LogNewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2LogNew> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    @Override
    public List<W2LogNewVo> queryList(W2LogNewBo bo) {
        LambdaQueryWrapper<W2LogNew> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

}
