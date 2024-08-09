package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2LineconfigVo;
import com.kmks.w2.domain.bo.W2LineconfigBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 路线管理Service接口
 *
 * @author Lynn
 * @date 2023-03-28
 */
public interface IW2LineconfigService {

    /**
     * 查询路线管理
     */
    W2LineconfigVo queryById(Long line);

    /**
     * 查询路线管理列表
     */
    TableDataInfo<W2LineconfigVo> queryPageList(W2LineconfigBo bo, PageQuery pageQuery);

    /**
     * 查询路线管理列表
     */
    List<W2LineconfigVo> queryList(W2LineconfigBo bo);

    /**
     * 新增路线管理
     */
    Boolean insertByBo(W2LineconfigBo bo);

    /**
     * 修改路线管理
     */
    Boolean updateByBo(W2LineconfigBo bo);

    /**
     * 校验并批量删除路线管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Map<String, Long> resetLineConfigMapCache();

    Map<String, Long> getLineConfigMap(String course);

    String getLineDm(Long line);
}
