package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.SysAuditLog;
import com.ruoyi.system.domain.vo.AuditLogAnalyseDto;
import com.ruoyi.system.domain.vo.SysAuditLogVo;

import java.util.List;

/**
 * 审计日志Mapper接口
 *
 * @author Lynn
 * @date 2024-07-11
 */
public interface SysAuditLogMapper extends BaseMapperPlus<SysAuditLogMapper, SysAuditLog, SysAuditLogVo> {

    List<AuditLogAnalyseDto> statisticsItems();
}
