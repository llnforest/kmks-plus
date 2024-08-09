package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.SysAuditLogBo;
import com.ruoyi.system.domain.vo.AuditLogAnalyseDto;
import com.ruoyi.system.domain.vo.SysAuditLogVo;

import java.util.Collection;
import java.util.List;

/**
 * 审计日志Service接口
 *
 * @author Lynn
 * @date 2024-07-11
 */
public interface ISysAuditLogService {

    /**
     * 查询审计日志
     */
    SysAuditLogVo queryById(Long auditId);

    /**
     * 查询审计日志列表
     */
    TableDataInfo<SysAuditLogVo> queryPageList(SysAuditLogBo bo, PageQuery pageQuery);

    /**
     * 查询审计日志列表
     */
    List<SysAuditLogVo> queryList(SysAuditLogBo bo);

    /**
     * 新增审计日志
     */
    Boolean insertByBo(SysAuditLogBo bo);

    /**
     * 修改审计日志
     */
    Boolean updateByBo(SysAuditLogBo bo);

    /**
     * 校验并批量删除审计日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<AuditLogAnalyseDto> statisticsItems();
}
