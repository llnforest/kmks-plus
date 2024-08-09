package com.kmks.w2.service;

import com.kmks.w2.domain.W2Flowlog;
import com.kmks.w2.domain.bo.AnalyseKfdmBo;
import com.kmks.w2.domain.vo.AnalyseKfdmVo;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 考试过程信息Service接口
 *
 * @author ruoyi
 * @date 2024-04-25
 */
public interface IW2FlowlogService {

    /**
     * 查询考试过程信息
     */
    W2FlowVo queryById(Long id);

    /**
     * 查询考试过程信息列表
     */
    TableDataInfo<W2FlowVo> queryPageList(W2FlowBo bo, PageQuery pageQuery);

    /**
     * 查询考试过程信息列表
     */
    List<W2FlowVo> queryList(W2FlowBo bo);

    List<AnalyseKfdmVo> getAnalyseKfxmVoList(AnalyseKfdmBo bo);

    /**
     * 新增考试过程信息
     */
    Boolean insertByBo(W2FlowBo bo);

    /**
     * 修改考试过程信息
     */
    Boolean updateByBo(W2FlowBo bo);

    /**
     * 校验并批量删除考试过程信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
