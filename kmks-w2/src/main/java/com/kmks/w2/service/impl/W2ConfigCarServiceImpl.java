package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.W2ConfigCar;
import com.kmks.w2.domain.W2ConfigDevice;
import com.kmks.w2.domain.bo.W2ConfigCarBo;
import com.kmks.w2.domain.vo.W2ConfigCarVo;
import com.kmks.w2.hcnet.service.HCNetService;
import com.kmks.w2.mapper.W2ConfigCarMapper;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.bean.BeanHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.service.IW2ConfigCarService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 车辆合码器Service业务层处理
 *
 * @author lynn
 * @date 2023-05-04
 */
@RequiredArgsConstructor
@Service
public class W2ConfigCarServiceImpl implements IW2ConfigCarService {

    private final W2ConfigCarMapper baseMapper;

    /**
     * 查询车辆合码器
     */
    @Override
    public W2ConfigCarVo queryById(String carno){
        return baseMapper.selectVoById(carno);
    }

    /**
     * 查询车辆合码器列表
     */
    @Override
    public TableDataInfo<W2ConfigCarVo> queryPageList(W2ConfigCarBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2ConfigCar> lqw = buildQueryWrapper(bo);
        Page<W2ConfigCarVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车辆合码器列表
     */
    @Override
    public List<W2ConfigCarVo> queryList(W2ConfigCarBo bo) {
        LambdaQueryWrapper<W2ConfigCar> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2ConfigCar> buildQueryWrapper(W2ConfigCarBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2ConfigCar> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增车辆合码器
     */
    @Override
    public Boolean insertByBo(W2ConfigCarBo bo) {
        W2ConfigCar add = BeanUtil.toBean(bo, W2ConfigCar.class);
        validEntityBeforeSave(add);
        LambdaQueryWrapper<W2ConfigCar> eq = Wrappers.lambdaQuery(W2ConfigCar.class).eq(W2ConfigCar::getCarno, add.getCarno());
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
     * 新增车辆合码器
     */
    @Override
    public Boolean insertBatch(Collection<W2ConfigCarVo> collection) {
        List<W2ConfigCar> collect = collection.stream().map(v -> BeanUtil.toBean(v, W2ConfigCar.class)).collect(Collectors.toList());
        return baseMapper.insertBatch(collect);
    }

    /**
     * 修改车辆合码器
     */
    @Override
    public Boolean updateByBo(W2ConfigCarBo bo) {
        W2ConfigCar update = BeanUtil.toBean(bo, W2ConfigCar.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2ConfigCar entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车辆合码器
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        HCNetService hcNetService = BeanHelper.getBean(HCNetService.class);
        hcNetService.resetData();
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
