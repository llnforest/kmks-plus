package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kmks.w2.domain.EquipmentBean;
import com.kmks.w2.domain.W2Cdxmbh;
import com.kmks.w2.domain.bo.W2CdxmbhBo;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.mapper.W2CdxmbhMapper;
import com.kmks.w2.service.IW2CdxmbhService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.redisData.RedisCdxmConfigData;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 场地项目编号Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class W2CdxmbhServiceImpl implements IW2CdxmbhService {

    private final W2CdxmbhMapper baseMapper;
    private final ISysConfigService configService;

    /**
     * 查询场地项目编号
     */
    @Override
    public W2CdxmbhVo queryById(Long nid){
        return baseMapper.selectVoById(nid);
    }

    /**
     * 查询场地项目编号列表
     */
    @Override
    public TableDataInfo<W2CdxmbhVo> queryPageList(W2CdxmbhBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Cdxmbh> lqw = buildQueryWrapper(bo);
        Page<W2CdxmbhVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询场地项目编号列表
     */
    @Override
    public List<W2CdxmbhVo> queryList(W2CdxmbhBo bo) {
        LambdaQueryWrapper<W2Cdxmbh> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Cdxmbh> buildQueryWrapper(W2CdxmbhBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Cdxmbh> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getNid() != null, W2Cdxmbh::getNid, bo.getNid());
        lqw.eq(bo.getParamdm() != null, W2Cdxmbh::getParamdm, bo.getParamdm());
        lqw.like(bo.getParamname() != null, W2Cdxmbh::getParamname, bo.getParamname());
        lqw.eq(bo.getSbxh() != null, W2Cdxmbh::getSbxh, bo.getSbxh());
        lqw.eq(bo.getMdm() != null, W2Cdxmbh::getMdm, bo.getMdm());
        lqw.eq(bo.getGadm() != null, W2Cdxmbh::getGadm, bo.getGadm());
        lqw.orderByDesc(W2Cdxmbh::getNid);
        return lqw;
    }

    /**
     * 新增场地项目编号
     */
    @Override
    public Boolean insertByBo(W2CdxmbhBo bo) {
        W2Cdxmbh add = BeanUtil.toBean(bo, W2Cdxmbh.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            resetCdxmConfigMapCache();
            bo.setNid(add.getNid());
        }
        return flag;
    }

    /**
     * 修改场地项目编号
     */
    @Override
    public Boolean updateByBo(W2CdxmbhBo bo) {
        W2Cdxmbh update = BeanUtil.toBean(bo, W2Cdxmbh.class);
        validEntityBeforeSave(update);
        if(baseMapper.updateById(update) > 0){
            resetCdxmConfigMapCache();
            return true;
        }
        return false;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Cdxmbh entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除场地项目编号
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        if(baseMapper.deleteBatchIds(ids) > 0){
            resetCdxmConfigMapCache();
            return true;
        }
        return false;
    }

    @Override
    public Page<EquipmentBean> listCdxmbhByLogNew(Page page) {
        return baseMapper.listCdxmbhByLogNew(page);
    }


    /**
     * 重置路线配置映射缓存
     *
     * @return {@link Map}<{@link String}, {@link Long}>
     */
    @Override
    public Map<String, W2CdxmbhVo>  resetCdxmConfigMapCache(){
        // 删除缓存
        RedisCdxmConfigData.deleteCdxmConfigData();
        // 重置缓存
        List<W2CdxmbhVo> w2CdxmbhVos = queryList(new W2CdxmbhBo());
        Map<String, W2CdxmbhVo> dataMap = w2CdxmbhVos.stream().collect(Collectors.toMap(v->String.valueOf(v.getParamdm()), v -> v));
        RedisCdxmConfigData.setCdxmConfigData(dataMap);
        return dataMap;

    }

    /**
     * 获取cdxm配置
     *
     * @param paramDm 参数dm
     * @return {@link W2CdxmbhVo}
     */
    @Override
    public W2CdxmbhVo getCdxmConfig(String paramDm) {
        Map<String, W2CdxmbhVo> cdxmMap = RedisCdxmConfigData.getCdxmConfigReturn();
        if(cdxmMap.containsKey(paramDm)){
            return cdxmMap.get(paramDm);
        }
        throw new FailException("未匹配到场地项目");
    }

    /**
     * 根据公安网代码转本地代码
     *
     * @param gadms gadms
     * @return {@link W2CdxmbhVo}
     */
    @Override
    public String getMdmByGadm(List gadms) {
        List<String> mdms = new ArrayList<>();
        Map<String, W2CdxmbhVo> cdxmMap = RedisCdxmConfigData.getCdxmConfigReturn();
        cdxmMap.entrySet().stream().forEach(entry ->{
            if(gadms.contains(entry.getValue().getGadm()) && !mdms.contains(entry.getValue().getMdm())){
                mdms.add(entry.getValue().getMdm());
            }
        });
        mdms.add(0,configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY));
        Collections.sort(mdms);
        return StringUtils.join(mdms, ",");
    }
}
