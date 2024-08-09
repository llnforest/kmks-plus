package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.ruoyi.common.redisData.RedisLineConfigData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2LineconfigBo;
import com.kmks.w2.domain.vo.W2LineconfigVo;
import com.kmks.w2.domain.W2Lineconfig;
import com.kmks.w2.mapper.W2LineconfigMapper;
import com.kmks.w2.service.IW2LineconfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 路线管理Service业务层处理
 *
 * @author Lynn
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class W2LineconfigServiceImpl implements IW2LineconfigService {

    private final W2LineconfigMapper baseMapper;

    /**
     * 查询路线管理
     */
    @Override
    public W2LineconfigVo queryById(Long line){
        return baseMapper.selectVoById(line);
    }

    /**
     * 查询路线管理列表
     */
    @Override
    public TableDataInfo<W2LineconfigVo> queryPageList(W2LineconfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Lineconfig> lqw = buildQueryWrapper(bo);
        Page<W2LineconfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询路线管理列表
     */
    @Override
    public List<W2LineconfigVo> queryList(W2LineconfigBo bo) {
        LambdaQueryWrapper<W2Lineconfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Lineconfig> buildQueryWrapper(W2LineconfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Lineconfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getLinecode()), W2Lineconfig::getLinecode, bo.getLinecode());
        lqw.eq(bo.getLinezt() != null, W2Lineconfig::getLinezt, bo.getLinezt());
        lqw.orderByAsc(W2Lineconfig::getLine);
        return lqw;
    }

    /**
     * 新增路线管理
     */
    @Override
    public Boolean insertByBo(W2LineconfigBo bo) {
        W2Lineconfig add = BeanUtil.toBean(bo, W2Lineconfig.class);
        validEntityBeforeSave(add);
        boolean flag;
        if(baseMapper.exists(Wrappers.lambdaQuery(W2Lineconfig.class).eq(W2Lineconfig::getLine,add.getLine()))){
            flag = baseMapper.updateById(add) > 0;
        }else{
            flag = baseMapper.insert(add) > 0;
        }
        if (flag) {
            bo.setLine(add.getLine());
            resetLineConfigMapCache();
        }
        return flag;
    }

    /**
     * 修改路线管理
     */
    @Override
    public Boolean updateByBo(W2LineconfigBo bo) {
        W2Lineconfig update = BeanUtil.toBean(bo, W2Lineconfig.class);
        validEntityBeforeSave(update);
        Boolean bool = baseMapper.updateById(update) > 0;
        if(bool){
            resetLineConfigMapCache();
        }
        return bool;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Lineconfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除路线管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        Boolean bool = baseMapper.deleteBatchIds(ids) > 0;
        if(bool){
            resetLineConfigMapCache();
        }
        return bool;
    }

    /**
     * 重置路线配置映射缓存
     *
     * @return {@link Map}<{@link String}, {@link Long}>
     */
    @Override
    public Map<String, Long>  resetLineConfigMapCache(){
        // 删除缓存
        RedisLineConfigData.deleteLineConfigData();
        // 重置缓存
        List<W2LineconfigVo> w2LineconfigVos = queryList(new W2LineconfigBo());
        Map<String, Long> dataMap = w2LineconfigVos.stream().collect(Collectors.toMap(W2LineconfigVo::getLinecode, W2LineconfigVo::getLine));
        RedisLineConfigData.setLineConfigData(dataMap);
        return dataMap;

    }

    @Override
    public Map<String, Long> getLineConfigMap(String course) {
        Map<String, Long> lineConfigMap = RedisLineConfigData.getLineConfigReturn();
        if(course.equals("3")){
            if(MapUtil.isEmpty(lineConfigMap)){
                lineConfigMap = resetLineConfigMapCache();
            }
        }
        return lineConfigMap;
    }

    /**
     * 获取线路代码
     *
     * @param line 线
     * @return {@link String}
     */
    @Override
    public String getLineDm(Long line){
        Map<String, Long> lineConfigMap = getLineConfigMap("3");
        for(Map.Entry<String,Long> entry:lineConfigMap.entrySet()){
            if(entry.getValue() == line){
                return entry.getKey();
            }
        }
        return "";
    }
}
