package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysSafeLog;
import com.ruoyi.system.domain.vo.SysSafeLogStatVo;
import com.ruoyi.system.domain.vo.SysSafeLogVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 安全日志Mapper接口
 *
 * @author ghgd
 * @date 2023-03-03
 */
public interface SysSafeLogMapper extends BaseMapperPlus<SysSafeLogMapper, SysSafeLog, SysSafeLogVo> {

    List<SysSafeLogStatVo> queryListState();
}
