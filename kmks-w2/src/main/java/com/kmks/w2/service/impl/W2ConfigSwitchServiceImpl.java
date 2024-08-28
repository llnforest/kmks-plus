package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2ConfigSwitch;
import com.kmks.w2.domain.W2KsxmdmJg;
import com.kmks.w2.domain.bo.W2ConfigSwitchBo;
import com.kmks.w2.domain.vo.W2ConfigSwitchVo;
import com.kmks.w2.hcnet.service.HCNetService;
import com.kmks.w2.mapper.W2ConfigSwitchMapper;
import com.kmks.w2.service.IW2ConfigSwitchService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.bean.BeanHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 项目监控Service业务层处理
 *
 * @author lynn
 * @date 2023-05-04
 */
@RequiredArgsConstructor
@Service
public class W2ConfigSwitchServiceImpl implements IW2ConfigSwitchService {

    private final W2ConfigSwitchMapper baseMapper;

    /**
     * 查询项目监控
     */
    @Override
    public W2ConfigSwitchVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询项目监控列表
     */
    @Override
    public TableDataInfo<W2ConfigSwitchVo> queryPageList(W2ConfigSwitchBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2ConfigSwitch> lqw = buildQueryWrapper(bo);
        Page<W2ConfigSwitchVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询项目监控列表
     */
    @Override
    public List<W2ConfigSwitchVo> queryList(W2ConfigSwitchBo bo) {
        LambdaQueryWrapper<W2ConfigSwitch> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2ConfigSwitch> buildQueryWrapper(W2ConfigSwitchBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2ConfigSwitch> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBitstreamtype() != null, W2ConfigSwitch::getBitstreamtype, bo.getBitstreamtype());
        return lqw;
    }

    /**
     * 新增项目监控
     */
    @Override
    public Boolean insertByBo(W2ConfigSwitchBo bo) {
        W2ConfigSwitch add = BeanUtil.toBean(bo, W2ConfigSwitch.class);
        validEntityBeforeSave(add);
        LambdaQueryWrapper<W2ConfigSwitch> eq = Wrappers.lambdaQuery(W2ConfigSwitch.class).eq(W2ConfigSwitch::getProjectcode, add.getProjectcode());
        boolean flag;
        if(baseMapper.exists(eq)){
            flag = baseMapper.updateById(add) > 0;
        }else{
            flag = baseMapper.insert(add) > 0;
        }
        HCNetService hcNetService = BeanHelper.getBean(HCNetService.class);
        hcNetService.resetData();
        return flag;
    }

    /**
     * 修改项目监控
     */
    @Override
    public Boolean updateByBo(W2ConfigSwitchBo bo) {
        W2ConfigSwitch update = BeanUtil.toBean(bo, W2ConfigSwitch.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2ConfigSwitch entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除项目监控
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        HCNetService hcNetService = BeanHelper.getBean(HCNetService.class);
        hcNetService.resetData();
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean insertBatch(Collection<W2ConfigSwitchVo> collection) {
        List<W2ConfigSwitch> collect = collection.stream().map(v -> BeanUtil.toBean(v, W2ConfigSwitch.class)).collect(Collectors.toList());
        return baseMapper.insertBatch(collect);
    }
}
