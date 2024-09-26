package com.kmks.w2.service;

import com.kmks.w2.domain.W2UserTest;
import com.kmks.w2.domain.vo.W2UserTestVo;
import com.kmks.w2.domain.bo.W2UserTestBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 测试数据Service接口
 *
 * @author lynn
 * @date 2024-09-24
 */
public interface IW2UserTestService {

    /**
     * 查询测试数据
     */
    W2UserTestVo queryById(Long id);

    /**
     * 查询测试数据列表
     */
    TableDataInfo<W2UserTestVo> queryPageList(W2UserTestBo bo, PageQuery pageQuery);

    /**
     * 查询测试数据列表
     */
    List<W2UserTestVo> queryList(W2UserTestBo bo);

    /**
     * 新增测试数据
     */
    Boolean insertByBo(W2UserTestBo bo);

    /**
     * 修改测试数据
     */
    Boolean updateByBo(W2UserTestBo bo);

    /**
     * 校验并批量删除测试数据信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
