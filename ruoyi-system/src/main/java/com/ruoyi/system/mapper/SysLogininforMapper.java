package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.domain.vo.SysLoginStatVo;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author Lion Li
 */
public interface SysLogininforMapper extends BaseMapperPlus<SysLogininforMapper, SysLogininfor, SysLogininfor> {

    List<SysLoginStatVo> selectLogininfoState();
}
