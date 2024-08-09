package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysAuditLog;
import com.ruoyi.system.domain.bo.SysAuditLogBo;
import com.ruoyi.system.domain.vo.AuditLogAnalyseDto;
import com.ruoyi.system.domain.vo.SysAuditLogVo;
import com.ruoyi.system.mapper.SysAuditLogMapper;
import com.ruoyi.system.service.ISysAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 审计日志Service业务层处理
 *
 * @author Lynn
 * @date 2024-07-11
 */
@RequiredArgsConstructor
@Service
public class SysAuditLogServiceImpl implements ISysAuditLogService {

    private final SysAuditLogMapper baseMapper;

    /**
     * 查询审计日志
     */
    @Override
    public SysAuditLogVo queryById(Long auditId){
        return baseMapper.selectVoById(auditId);
    }

    /**
     * 查询审计日志列表
     */
    @Override
    public TableDataInfo<SysAuditLogVo> queryPageList(SysAuditLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysAuditLog> lqw = buildQueryWrapper(bo);
        Page<SysAuditLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询审计日志列表
     */
    @Override
    public List<SysAuditLogVo> queryList(SysAuditLogBo bo) {
        LambdaQueryWrapper<SysAuditLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysAuditLog> buildQueryWrapper(SysAuditLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysAuditLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUsername()), SysAuditLog::getUsername, bo.getUsername());
        lqw.like(StringUtils.isNotBlank(bo.getTableName()), SysAuditLog::getTableName, bo.getTableName());
        lqw.eq(StringUtils.isNotBlank(bo.getTableComment()), SysAuditLog::getTableComment, bo.getTableComment());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), SysAuditLog::getIpAddress, bo.getIpAddress());
        lqw.between(params.get("beginOperationTime") != null && params.get("endOperationTime") != null,
            SysAuditLog::getOperationTime ,params.get("beginOperationTime"), params.get("endOperationTime"));
        lqw.eq(StringUtils.isNotBlank(bo.getOperationType()), SysAuditLog::getOperationType, bo.getOperationType());
        lqw.like(StringUtils.isNotBlank(bo.getModuleName()), SysAuditLog::getModuleName, bo.getModuleName());
        lqw.orderByDesc(SysAuditLog::getAuditId);
        return lqw;
    }

    /**
     * 新增审计日志
     */
    @Override
    public Boolean insertByBo(SysAuditLogBo bo) {
        SysAuditLog add = BeanUtil.toBean(bo, SysAuditLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuditId(add.getAuditId());
        }
        return flag;
    }

    /**
     * 修改审计日志
     */
    @Override
    public Boolean updateByBo(SysAuditLogBo bo) {
        SysAuditLog update = BeanUtil.toBean(bo, SysAuditLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysAuditLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除审计日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<AuditLogAnalyseDto> statisticsItems() {
        return baseMapper.statisticsItems();
    }
}
