package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2KsyVo;
import com.kmks.w2.domain.bo.W2KsyBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 考官信息Service接口
 *
 * @author lynn
 * @date 2023-04-28
 */
public interface IW2KsyService {

    /**
     * 查询考官信息
     */
    W2KsyVo queryById(String xh);

    /**
     * 查询考官信息列表
     */
    TableDataInfo<W2KsyVo> queryPageList(W2KsyBo bo, PageQuery pageQuery);

    /**
     * 查询考官信息列表
     */
    List<W2KsyVo> queryList(W2KsyBo bo);

    /**
     * 新增考官信息
     */
    Boolean insertByBo(W2KsyBo bo);

    /**
     * 修改考官信息
     */
    Boolean updateByBo(W2KsyBo bo);

    /**
     * 校验并批量删除考官信息信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
