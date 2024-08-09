package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.hash.Hash;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2ConfigBo;
import com.kmks.w2.domain.vo.W2ConfigVo;
import com.kmks.w2.domain.W2Config;
import com.kmks.w2.mapper.W2ConfigMapper;
import com.kmks.w2.service.IW2ConfigService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 驾考参数Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-01
 */
@RequiredArgsConstructor
@Service
public class W2ConfigServiceImpl implements IW2ConfigService {

    private final W2ConfigMapper baseMapper;

    /**
     * 查询驾考参数
     */
    @Override
    public W2ConfigVo queryById(Long lIncode){
        return baseMapper.selectVoById(lIncode);
    }

    /**
     * 查询驾考参数列表
     */
    @Override
    public TableDataInfo<W2ConfigVo> queryPageList(W2ConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Config> lqw = buildQueryWrapper(bo);
        Page<W2ConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询驾考参数列表
     */
    @Override
    public List<W2ConfigVo> queryList(W2ConfigBo bo) {
        LambdaQueryWrapper<W2Config> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Config> buildQueryWrapper(W2ConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Config> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getIFlag() != null, W2Config::getIFlag, bo.getIFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getSFlag()), W2Config::getSFlag, bo.getSFlag());
        lqw.like(StringUtils.isNotBlank(bo.getSName()), W2Config::getSName, bo.getSName());
        lqw.eq(bo.getIValue() != null, W2Config::getIValue, bo.getIValue());
        lqw.eq(StringUtils.isNotBlank(bo.getSValue()), W2Config::getSValue, bo.getSValue());
        lqw.orderByAsc(W2Config::getLIncode);
        return lqw;
    }

    /**
     * 新增驾考参数
     */
    @Override
    public Boolean insertByBo(W2ConfigBo bo) {
        W2Config add = BeanUtil.toBean(bo, W2Config.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLIncode(add.getLIncode());
        }
        return flag;
    }

    /**
     * 修改驾考参数
     */
    @Override
    public Boolean updateByBo(W2ConfigBo bo) {
        W2Config update = BeanUtil.toBean(bo, W2Config.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Config entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除驾考参数
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Map<String, String> getCodeValueMap(W2ConfigBo bo) {
        List<W2ConfigVo> w2ConfigVoList= queryList(bo);
        Map<String,String> configMap = new HashMap<>();
        for(W2ConfigVo config:w2ConfigVoList){
            configMap.put(String.valueOf(config.getLIncode()),config.getSValue());
        }
        return configMap;
    }

    //设置报表开头信息
    @Override
    public void setReportConfigInfo(Map<String, String> map){
        Map<String, String> configMap = this.getConfigMap();
        map.put("title",configMap.get("400_3"));
        map.put("ksdd",configMap.get("1_8"));
        map.put("kskm",configMap.get("400_4"));
        map.put("bkkskm",configMap.get("400_5"));
    }

    private Map<String,String> getConfigMap(){
        LambdaQueryWrapper<W2Config> lqw = Wrappers.lambdaQuery();
        lqw.select(W2Config::getIFlag,W2Config::getIValue,W2Config::getSValue);
        List<W2ConfigVo> w2ConfigVoList = baseMapper.selectVoList(lqw);
        // value 值有为null不行
//        Map<String,String> configMap = w2ConfigVoList.stream().collect(Collectors.toMap(s -> s.getIFlag() +"_"+ s.getIValue(), W2ConfigVo::getSValue));
        HashMap<String, String> map = new HashMap<>();
        Map<String,String> configMap = CollectionUtil.toMap(w2ConfigVoList,map,s -> s.getIFlag() +"_"+ s.getIValue(), W2ConfigVo::getSValue);
        return configMap;
    }
}
