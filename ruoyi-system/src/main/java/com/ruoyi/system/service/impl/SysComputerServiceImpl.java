package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysComputer;
import com.ruoyi.system.domain.bo.SysComputerBo;
import com.ruoyi.system.domain.vo.SysComputerVo;
import com.ruoyi.system.mapper.SysComputerMapper;
import com.ruoyi.system.service.ISysComputerService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 管理计算机访问Service业务层处理
 *
 * @author Lynn
 * @date 2024-07-10
 */
@RequiredArgsConstructor
@Service
public class SysComputerServiceImpl implements ISysComputerService {

    private final SysComputerMapper baseMapper;
    
    private final ISysConfigService configService;

    /**
     * 查询管理计算机访问
     */
    @Override
    public SysComputerVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 验证ip和mac访问
     *
     * @param ip  ip
     * @param mac 雨衣
     * @return {@link Boolean}
     */
    @Override
    public Boolean visitedByIpAndMac(String ip, String mac){
        SysComputerBo bo = new SysComputerBo();
        bo.setIp(ip);
        bo.setMac(mac);
        return baseMapper.exists(buildQueryWrapper(bo));
    }
    /**
     * 查询管理计算机访问列表
     */
    @Override
    public TableDataInfo<SysComputerVo> queryPageList(SysComputerBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysComputer> lqw = buildQueryWrapper(bo);
        Page<SysComputerVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询管理计算机访问列表
     */
    @Override
    public List<SysComputerVo> queryList(SysComputerBo bo) {
        LambdaQueryWrapper<SysComputer> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysComputer> buildQueryWrapper(SysComputerBo bo) {
        LambdaQueryWrapper<SysComputer> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getComputerName()), SysComputer::getComputerName, bo.getComputerName());
        lqw.eq(StringUtils.isNotBlank(bo.getComputerLocation()), SysComputer::getComputerLocation, bo.getComputerLocation());
        lqw.eq(StringUtils.isNotBlank(bo.getIp()), SysComputer::getIp, bo.getIp());
        lqw.eq(StringUtils.isNotBlank(bo.getMac()), SysComputer::getMac, bo.getMac());
        lqw.like(StringUtils.isNotBlank(bo.getRemarks()), SysComputer::getRemarks, bo.getRemarks());
        lqw.eq(SysComputer::getSchoolId,configService.selectConfigByKey(CacheNames.SCHOOL_ID));
        return lqw;
    }

    /**
     * 新增管理计算机访问
     */
    @Override
    public Boolean insertByBo(SysComputerBo bo) {
        SysComputer add = BeanUtil.toBean(bo, SysComputer.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改管理计算机访问
     */
    @Override
    public Boolean updateByBo(SysComputerBo bo) {
        SysComputer update = BeanUtil.toBean(bo, SysComputer.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysComputer entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除管理计算机访问
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
