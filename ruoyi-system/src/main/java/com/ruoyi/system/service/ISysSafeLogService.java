package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysSafeLog;
import com.ruoyi.system.domain.vo.SysSafeLogStatVo;
import com.ruoyi.system.domain.vo.SysSafeLogVo;
import com.ruoyi.system.domain.bo.SysSafeLogBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 安全日志Service接口
 *
 * @author ghgd
 * @date 2023-03-03
 */
public interface ISysSafeLogService {

    /**
     * 查询安全日志
     */
    SysSafeLogVo queryById(Long id);

    /**
     * 查询安全日志列表
     */
    TableDataInfo<SysSafeLogVo> queryPageList(SysSafeLogBo bo, PageQuery pageQuery);

    /**
     * 查询安全日志列表
     */
    List<SysSafeLogVo> queryList(SysSafeLogBo bo);

    /**
     * 新增安全日志
     */
    Boolean insertByBo(SysSafeLogBo bo);

    void insertSafeLog(SysSafeLog safeLog);

    /**
     * 修改安全日志
     */
    Boolean updateByBo(SysSafeLogBo bo);

    /**
     * 校验并批量删除安全日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 统计
     */
    List<SysSafeLogStatVo> queryListState();

    /**
     * 加入安全日志事件
     */
    void addSafeLoginEvent(String username, String category, String operation, String state,String ywtype,String content);
}
