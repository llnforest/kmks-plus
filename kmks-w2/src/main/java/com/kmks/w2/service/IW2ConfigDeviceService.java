package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2ConfigDeviceBo;
import com.kmks.w2.domain.vo.W2ConfigDeviceVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备合码器Service接口
 *
 * @author lynn
 * @date 2023-05-04
 */
public interface IW2ConfigDeviceService {

    /**
     * 查询设备合码器
     */
    W2ConfigDeviceVo queryById(Long deviceno);

    /**
     * 查询设备合码器列表
     */
    TableDataInfo<W2ConfigDeviceVo> queryPageList(W2ConfigDeviceBo bo, PageQuery pageQuery);

    /**
     * 查询设备合码器列表
     */
    List<W2ConfigDeviceVo> queryList(W2ConfigDeviceBo bo);

    /**
     * 新增设备合码器
     */
    Boolean insertByBo(W2ConfigDeviceBo bo);

    /**
     * 修改设备合码器
     */
    Boolean updateByBo(W2ConfigDeviceBo bo);

    /**
     * 校验并批量删除设备合码器信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean insertBatch(Collection<W2ConfigDeviceVo> collection);
}
