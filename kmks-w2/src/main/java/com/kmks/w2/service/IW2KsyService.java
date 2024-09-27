package com.kmks.w2.service;

import com.kmks.jianguanold.domain.vo.A17C04Vo;
import com.kmks.w2.domain.vo.W2KsyVo;
import com.kmks.w2.domain.bo.W2KsyBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 考试员信息Service接口
 *
 * @author lynn
 * @date 2023-04-28
 */
public interface IW2KsyService {

    /**
     * 查询考试员信息
     */
    W2KsyVo queryById(String xh);

    /**
     * 查询考试员信息列表
     */
    TableDataInfo<W2KsyVo> queryPageList(W2KsyBo bo, PageQuery pageQuery);

    /**
     * 查询考试员信息列表
     */
    List<W2KsyVo> queryList(W2KsyBo bo);

    /**
     * 新增考试员信息
     */
    Boolean insertByBo(W2KsyBo bo);

    /**
     * 修改考试员信息
     */
    Boolean updateByBo(W2KsyBo bo);

    /**
     * 校验并批量删除考试员信息信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    Boolean downLoadKsy(List<A17C04Vo.Body> list);
}
