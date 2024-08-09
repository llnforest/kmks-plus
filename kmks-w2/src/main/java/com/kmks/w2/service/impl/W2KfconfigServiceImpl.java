package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.service.IW2KfconfigService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.redisData.RedisKfConfigData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2KfconfigBo;
import com.kmks.w2.domain.vo.W2KfconfigVo;
import com.kmks.w2.domain.W2Kfconfig;
import com.kmks.w2.mapper.W2KfconfigMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 评判参数Service业务层处理
 *
 * @author Lynn
 * @date 2023-03-21
 */
@DS("param")
@RequiredArgsConstructor
@Service
public class W2KfconfigServiceImpl implements IW2KfconfigService {

    private final W2KfconfigMapper baseMapper;
    private final SysConfigServiceImpl configService;

    /**
     * 查询评判参数
     */
    @Override
    public W2KfconfigVo queryById(String gakey){
        return baseMapper.selectVoById(gakey);
    }

    /**
     * 查询评判参数列表
     */
    @Override
    public TableDataInfo<W2KfconfigVo> queryPageList(W2KfconfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Kfconfig> lqw = buildQueryWrapper(bo);
        Page<W2KfconfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }



    /**
     * 查询评判参数列表
     */
    @Override
    public List<W2KfconfigVo> queryList(W2KfconfigBo bo) {
        LambdaQueryWrapper<W2Kfconfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Kfconfig> buildQueryWrapper(W2KfconfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Kfconfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getGakey()), W2Kfconfig::getGakey, bo.getGakey());
        lqw.eq(StringUtils.isNotBlank(bo.getGakfmc()), W2Kfconfig::getGakfmc, bo.getGakfmc());
        lqw.like(StringUtils.isNotBlank(bo.getKfmc()), W2Kfconfig::getKfmc, bo.getKfmc());
        lqw.eq(StringUtils.isNotBlank(bo.getGakfdm()), W2Kfconfig::getGakfdm, bo.getGakfdm());
        lqw.eq(StringUtils.isNotBlank(bo.getAutoflag()), W2Kfconfig::getAutoflag, bo.getAutoflag());
        lqw.eq(StringUtils.isNotBlank(bo.getKsxm()), W2Kfconfig::getKsxm, bo.getKsxm());
        lqw.eq(StringUtils.isNotBlank(bo.getParamtype()), W2Kfconfig::getParamtype, bo.getParamtype());
        lqw.in(W2Kfconfig::getKskm,configService.selectConfigByKey(CacheNames.COURSE_KEY).split(","));
        lqw.in(String.valueOf(params.get("judgeType")).equals("1"), W2Kfconfig::getParamtype, 0,4);
        lqw.in(String.valueOf(params.get("judgeType")).equals("2"), W2Kfconfig::getParamtype, 1,2,3);
        lqw.orderByAsc(W2Kfconfig::getKsxm);
        return lqw;
    }

    /**
     * 新增评判参数
     */
    @Override
    public Boolean insertByBo(W2KfconfigBo bo) {
        W2Kfconfig add = BeanUtil.toBean(bo, W2Kfconfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setGakey(add.getGakey());
        }
        return flag;
    }

    /**
     * 修改评判参数
     */
    @Override
    public Boolean updateByBo(W2KfconfigBo bo) {
        W2Kfconfig update = BeanUtil.toBean(bo, W2Kfconfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 在ids中查询
     *
     */
    @Override
    public List<W2Kfconfig>  queryInIds(String ids){
        LambdaQueryWrapper<W2Kfconfig> lqw = Wrappers.lambdaQuery();
        lqw.in(StringUtils.isNotBlank(ids),W2Kfconfig::getId,ids.split(";"));
        List<W2Kfconfig> w2Kfconfigs = baseMapper.selectList(lqw);
        return w2Kfconfigs;
    }
    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Kfconfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除评判参数
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 重置扣分配置映射缓存
     *
     * @return {@link Map}<{@link String}, {@link Long}>
     */
    @Override
    public Map<String, W2KfconfigVo>  resetKfConfigMapCache(){
        // 删除缓存
        RedisKfConfigData.deleteKfConfigData();
        // 重置缓存
        W2KfconfigBo kfconfigBo = new W2KfconfigBo();
        List<W2KfconfigVo> w2KfVos = queryList(new W2KfconfigBo());
        Map<String, W2KfconfigVo> dataMap = w2KfVos.stream()
                .filter(v->StringUtils.isNotBlank(v.getGakfdm()))
                .collect(Collectors.toMap(v->v.getGakfdm(), v -> v));
        RedisKfConfigData.setKfConfigData(dataMap);
        return dataMap;

    }

    /**
     * 获取扣分配置
     *
     * @param kfdm 扣分代码
     * @return {@link W2CdxmbhVo}
     */
    @Override
    public W2KfconfigVo getKfConfig(String kfdm) {
        Map<String, W2KfconfigVo> cdxmMap = RedisKfConfigData.getKfConfigReturn();
        if(cdxmMap.containsKey(kfdm)){
            return cdxmMap.get(kfdm);
        }
        throw new FailException("未匹配到扣分代码");
    }

    /**
     * 按组获取kf配置
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link W2KfconfigVo}>>
     */
    @Override
    public Map<String, List<W2KfconfigVo>> getKfConfigByGroup(){
        W2KfconfigBo kfconfigBo = new W2KfconfigBo();
       List<W2KfconfigVo> w2KfconfigVos = queryList(kfconfigBo);
        Map<String, List<W2KfconfigVo>> collect = w2KfconfigVos.stream().collect(Collectors.groupingBy(W2KfconfigVo::getGakfmc));
        return collect;
    }
}
