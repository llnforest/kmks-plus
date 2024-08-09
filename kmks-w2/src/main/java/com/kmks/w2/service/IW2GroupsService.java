package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2GroupsBo;
import com.kmks.w2.domain.vo.W2GroupsVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 明细分组信息Service接口
 *
 * @author lynn
 * @date 2023-04-28
 */
public interface IW2GroupsService {

    /**
     * 查询明细分组信息
     */
    W2GroupsVo queryById(String xh);

    /**
     * 查询明细分组信息列表
     */
    TableDataInfo<W2GroupsVo> queryPageList(W2GroupsBo bo, PageQuery pageQuery);

    /**
     * 查询明细分组信息列表
     */
    List<W2GroupsVo> queryList(W2GroupsBo bo);

    /**
     * 新增明细分组信息
     */
    Boolean insertByBo(W2GroupsBo bo);

    /**
     * 修改明细分组信息
     */
    Boolean updateByBo(W2GroupsBo bo);

    /**
     * 校验并批量删除明细分组信息信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
