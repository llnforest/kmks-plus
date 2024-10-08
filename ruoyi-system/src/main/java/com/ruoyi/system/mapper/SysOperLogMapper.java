package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.vo.SysOperStatisticsLogVo;

import java.util.List;

/**
 * 操作日志 数据层
 *
 * @author Lion Li
 */
public interface SysOperLogMapper extends BaseMapperPlus<SysOperLogMapper, SysOperLog, SysOperLog> {

    public List<SysOperStatisticsLogVo> statisticsItems();
}
