package com.kmks.w2.service;

import com.kmks.w2.domain.vo.GhSafepeopleVo;
import com.kmks.w2.domain.bo.GhSafepeopleBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 安全员管理Service接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IGhSafepeopleService {

    /**
     * 查询安全员管理
     */
    GhSafepeopleVo queryById(String sZjhm);

    /**
     * 查询安全员管理列表
     */
    TableDataInfo<GhSafepeopleVo> queryPageList(GhSafepeopleBo bo, PageQuery pageQuery);

    /**
     * 查询安全员管理列表
     */
    List<GhSafepeopleVo> queryList(GhSafepeopleBo bo);

    /**
     * 新增安全员管理
     */
    Boolean insertByBo(GhSafepeopleBo bo);

    /**
     * 修改安全员管理
     */
    Boolean updateByBo(GhSafepeopleBo bo);

    /**
     * 校验并批量删除安全员管理信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
