package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2SystemVo;
import com.kmks.w2.domain.bo.W2SystemBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 基础编码Service接口
 *
 * @author ruoyi
 * @date 2023-03-27
 */
public interface IW2SystemService {

    /**
     * 查询基础编码
     */
    W2SystemVo queryById(Long nid);

    /**
     * 根据nid 和类型查询
     */
    W2SystemVo selectVoByIdAndType(Long nid);

    /**
     * 查询基础编码列表
     */
    TableDataInfo<W2SystemVo> queryPageList(W2SystemBo bo, PageQuery pageQuery);

    /**
     * 查询基础编码列表
     */
    List<W2SystemVo> queryList(W2SystemBo bo);

    /**
     * 新增基础编码
     */
    Boolean insertByBo(W2SystemBo bo);

    /**
     * 修改基础编码
     */
    Boolean updateByBo(W2SystemBo bo);

    /**
     * 校验并批量删除基础编码信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
