package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2FactoryBo;
import com.kmks.w2.domain.vo.W2FactoryVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 考点信息Service接口
 *
 * @author lynn
 * @date 2023-04-28
 */
public interface IW2FactoryService {

    /**
     * 查询考点信息
     */
    W2FactoryVo queryById(String xh);

    /**
     * 查询考点信息列表
     */
    TableDataInfo<W2FactoryVo> queryPageList(W2FactoryBo bo, PageQuery pageQuery);

    /**
     * 查询考点信息列表
     */
    List<W2FactoryVo> queryList(W2FactoryBo bo);

    /**
     * 新增考点信息
     */
    Boolean insertByBo(W2FactoryBo bo);

    /**
     * 修改考点信息
     */
    Boolean updateByBo(W2FactoryBo bo);

    /**
     * 校验并批量删除考点信息信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
