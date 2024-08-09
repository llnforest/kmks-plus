package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2GroupsTempBo;
import com.kmks.w2.domain.vo.W2GroupsTempVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 查询下载信息Service接口
 *
 * @author lynn
 * @date 2023-04-28
 */
public interface IW2GroupsTempService {

    /**
     * 查询查询下载信息
     */
    W2GroupsTempVo queryById(String lsh);

    /**
     * 查询查询下载信息列表
     */
    TableDataInfo<W2GroupsTempVo> queryPageList(W2GroupsTempBo bo, PageQuery pageQuery);

    /**
     * 查询查询下载信息列表
     */
    List<W2GroupsTempVo> queryList(W2GroupsTempBo bo);

    /**
     * 新增查询下载信息
     */
    Boolean insertByBo(W2GroupsTempBo bo);

    /**
     * 修改查询下载信息
     */
    Boolean updateByBo(W2GroupsTempBo bo);

    /**
     * 校验并批量删除查询下载信息信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
