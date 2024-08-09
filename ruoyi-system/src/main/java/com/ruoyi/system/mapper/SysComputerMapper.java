package com.ruoyi.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.SysComputer;
import com.ruoyi.system.domain.vo.SysComputerVo;

/**
 * 管理计算机访问Mapper接口
 *
 * @author Lynn
 * @date 2024-07-10
 */
@DS("param")
public interface SysComputerMapper extends BaseMapperPlus<SysComputerMapper, SysComputer, SysComputerVo> {

}
