package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.SysComputerBo;
import com.ruoyi.system.domain.vo.SysComputerVo;

import java.util.Collection;
import java.util.List;

/**
 * 管理计算机访问Service接口
 *
 * @author Lynn
 * @date 2024-07-10
 */
public interface ISysComputerService {

    /**
     * 查询管理计算机访问
     */
    SysComputerVo queryById(Long id);

    Boolean visitedByIpAndMac(String ip, String mac);

    /**
     * 查询管理计算机访问列表
     */
    TableDataInfo<SysComputerVo> queryPageList(SysComputerBo bo, PageQuery pageQuery);

    /**
     * 查询管理计算机访问列表
     */
    List<SysComputerVo> queryList(SysComputerBo bo);

    /**
     * 新增管理计算机访问
     */
    Boolean insertByBo(SysComputerBo bo);

    /**
     * 修改管理计算机访问
     */
    Boolean updateByBo(SysComputerBo bo);

    /**
     * 校验并批量删除管理计算机访问信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
