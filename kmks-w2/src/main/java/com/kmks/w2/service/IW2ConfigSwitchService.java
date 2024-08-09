package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2ConfigSwitchBo;
import com.kmks.w2.domain.vo.W2ConfigSwitchVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 项目监控Service接口
 *
 * @author lynn
 * @date 2023-05-04
 */
public interface IW2ConfigSwitchService {

    /**
     * 查询项目监控
     */
    W2ConfigSwitchVo queryById(Long id);

    /**
     * 查询项目监控列表
     */
    TableDataInfo<W2ConfigSwitchVo> queryPageList(W2ConfigSwitchBo bo, PageQuery pageQuery);

    /**
     * 查询项目监控列表
     */
    List<W2ConfigSwitchVo> queryList(W2ConfigSwitchBo bo);

    /**
     * 新增项目监控
     */
    Boolean insertByBo(W2ConfigSwitchBo bo);

    /**
     * 修改项目监控
     */
    Boolean updateByBo(W2ConfigSwitchBo bo);

    /**
     * 校验并批量删除项目监控信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean insertBatch(Collection<W2ConfigSwitchVo> collection);
}
