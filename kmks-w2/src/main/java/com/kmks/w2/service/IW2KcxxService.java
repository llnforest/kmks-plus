package com.kmks.w2.service;

import com.kmks.w2.domain.dto.JudgeDto;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 过程明细Service接口
 *
 * @author lynn
 * @date 2023-03-14
 */
public interface IW2KcxxService {

    /**
     * 查询过程明细
     */
    W2KcxxVo queryById(Long id);

    /**
     * 查询过程明细列表
     */
    TableDataInfo<W2KcxxVo> queryPageList(W2KcxxBo bo, PageQuery pageQuery);

    /**
     * 查询过程明细列表
     */
    List<W2KcxxVo> queryList(W2KcxxBo bo);

    W2KcxxVo queryOne(W2KcxxBo bo);

    Boolean exists(W2KcxxBo bo);

    /**
     * 新增过程明细
     */
    Boolean insertByBo(W2KcxxBo bo);

    /**
     * 修改过程明细
     */
    Boolean updateByBo(W2KcxxBo bo);

    /**
     * 校验并批量删除过程明细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean judge(JudgeDto judgeDto);

    Boolean resetKcxxCheck();
}
