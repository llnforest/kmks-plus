package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2ConfigCarBo;
import com.kmks.w2.domain.vo.W2ConfigCarVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 车辆合码器Service接口
 *
 * @author lynn
 * @date 2023-05-04
 */
public interface IW2ConfigCarService {

    /**
     * 查询车辆合码器
     */
    W2ConfigCarVo queryById(String carno);

    /**
     * 查询车辆合码器列表
     */
    TableDataInfo<W2ConfigCarVo> queryPageList(W2ConfigCarBo bo, PageQuery pageQuery);

    /**
     * 查询车辆合码器列表
     */
    List<W2ConfigCarVo> queryList(W2ConfigCarBo bo);

    /**
     * 新增车辆合码器
     */
    Boolean insertByBo(W2ConfigCarBo bo);

    Boolean insertBatch(Collection<W2ConfigCarVo> collection);

    /**
     * 修改车辆合码器
     */
    Boolean updateByBo(W2ConfigCarBo bo);

    /**
     * 校验并批量删除车辆合码器信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
