package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.BeanCopyUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2GpscontentBo;
import com.kmks.w2.domain.vo.W2GpscontentVo;
import com.kmks.w2.domain.W2Gpscontent;
import com.kmks.w2.mapper.W2GpscontentMapper;
import com.kmks.w2.service.IW2GpscontentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 轨迹数据Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-19
 */
@RequiredArgsConstructor
@Service
public class W2GpscontentServiceImpl implements IW2GpscontentService {

    private final W2GpscontentMapper baseMapper;

    /**
     * 查询轨迹数据
     */
    @Override
    public W2GpscontentVo queryById(Long timeid){
        return baseMapper.selectVoById(timeid);
    }

    /**
     * 查询轨迹数据列表
     */
    @Override
    public TableDataInfo<W2GpscontentVo> queryPageList(W2GpscontentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Gpscontent> lqw = buildQueryWrapper(bo);
        Page<W2GpscontentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轨迹数据列表
     */
    @Override
    public List<W2GpscontentVo> queryList(W2GpscontentBo bo) {
        LambdaQueryWrapper<W2Gpscontent> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询轨迹数据列表(分表)
     */
    @Override
    public <T> List<T> queryListByTable(W2GpscontentBo bo, Class<T> clazz) {
        return BeanCopyUtils.copyList(baseMapper.selectListByTable(bo), clazz);
    }

    private LambdaQueryWrapper<W2Gpscontent> buildQueryWrapper(W2GpscontentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Gpscontent> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTimeid() != null, W2Gpscontent::getTimeid, bo.getTimeid());
        lqw.eq(StringUtils.isNotBlank(bo.getGpscon1()), W2Gpscontent::getGpscon1, bo.getGpscon1());
        lqw.eq(StringUtils.isNotBlank(bo.getGpscon2()), W2Gpscontent::getGpscon2, bo.getGpscon2());
        lqw.eq(StringUtils.isNotBlank(bo.getSigcon()), W2Gpscontent::getSigcon, bo.getSigcon());
        lqw.eq(StringUtils.isNotBlank(bo.getKscon()), W2Gpscontent::getKscon, bo.getKscon());
        lqw.eq(bo.getSendrq() != null, W2Gpscontent::getSendrq, bo.getSendrq());
        lqw.eq(StringUtils.isNotBlank(bo.getCarno()), W2Gpscontent::getCarno, bo.getCarno());
        lqw.eq(StringUtils.isNotBlank(bo.getSfzhm()), W2Gpscontent::getSfzhm, bo.getSfzhm());
        lqw.eq(StringUtils.isNotBlank(bo.getDjc()), W2Gpscontent::getDjc, bo.getDjc());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2Gpscontent::getKcbh, bo.getKcbh());
        lqw.eq(StringUtils.isNotBlank(bo.getLsh()), W2Gpscontent::getLsh, bo.getLsh());
        lqw.eq(StringUtils.isNotBlank(bo.getKchp()), W2Gpscontent::getKchp, bo.getKchp());
        return lqw;
    }

    /**
     * 新增轨迹数据
     */
    @Override
    public Boolean insertByBo(W2GpscontentBo bo) {
        W2Gpscontent add = BeanUtil.toBean(bo, W2Gpscontent.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTimeid(add.getTimeid());
        }
        return flag;
    }

    /**
     * 修改轨迹数据
     */
    @Override
    public Boolean updateByBo(W2GpscontentBo bo) {
        W2Gpscontent update = BeanUtil.toBean(bo, W2Gpscontent.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Gpscontent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除轨迹数据
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
