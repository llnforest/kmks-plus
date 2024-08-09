package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysUserBlack;
import com.ruoyi.system.domain.vo.SysUserBlackVo;
import com.ruoyi.system.domain.bo.SysUserBlackBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 黑名单管理Service接口
 *
 * @author Lynn
 * @date 2023-03-07
 */
public interface ISysUserBlackService {

    /**
     * 查询黑名单管理
     */
    SysUserBlackVo queryById(Long id);

    /**
     * 查询黑名单管理列表
     */
    TableDataInfo<SysUserBlackVo> queryPageList(SysUserBlackBo bo, PageQuery pageQuery);

    /**
     * 查询黑名单管理列表
     */
    List<SysUserBlackVo> queryList(SysUserBlackBo bo);

    /**
     * 新增黑名单管理
     */
    Boolean insertByBo(SysUserBlackBo bo);

    /**
     * 修改黑名单管理
     */
    Boolean updateByBo(SysUserBlackBo bo);

    /**
     * 校验并批量删除黑名单管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 拉黑事件
     */
    void addBlackEvent(String blackName, String blackType, String remark);

    /**
     * 校验并解锁黑名单管理信息
     */
    Boolean unLock(Collection<Long> ids);
}
