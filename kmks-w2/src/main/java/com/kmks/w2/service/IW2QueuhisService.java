package com.kmks.w2.service;

import com.kmks.w2.domain.W2Queuhis;
import com.kmks.w2.domain.vo.W2QueuhisVo;
import com.kmks.w2.domain.bo.W2QueuhisBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2024-04-11
 */
public interface IW2QueuhisService {

    /**
     * 查询【请填写功能名称】
     */
    W2QueuhisVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<W2QueuhisVo> queryPageList(W2QueuhisBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<W2QueuhisVo> queryList(W2QueuhisBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(W2QueuhisBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(W2QueuhisBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
