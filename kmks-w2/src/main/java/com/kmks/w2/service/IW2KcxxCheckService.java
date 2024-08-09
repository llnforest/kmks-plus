package com.kmks.w2.service;

import com.kmks.w2.domain.W2KcxxCheck;
import com.kmks.w2.domain.vo.W2KcxxCheckVo;
import com.kmks.w2.domain.bo.W2KcxxCheckBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 考车自检Service接口
 *
 * @author Lynn
 * @date 2024-06-19
 */
public interface IW2KcxxCheckService {

    /**
     * 查询考车自检
     */
    W2KcxxCheckVo queryById(String kcbh);

    /**
     * 查询考车自检列表
     */
    TableDataInfo<W2KcxxCheckVo> queryPageList(W2KcxxCheckBo bo, PageQuery pageQuery);

    /**
     * 查询考车自检列表
     */
    List<W2KcxxCheckVo> queryList(W2KcxxCheckBo bo);

    /**
     * 新增考车自检
     */
    Boolean insertByBo(W2KcxxCheckBo bo);

    /**
     * 修改考车自检
     */
    Boolean updateByBo(W2KcxxCheckBo bo);

    /**
     * 校验并批量删除考车自检信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
