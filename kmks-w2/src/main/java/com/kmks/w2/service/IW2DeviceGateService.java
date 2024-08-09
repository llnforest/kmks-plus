package com.kmks.w2.service;

import com.kmks.w2.domain.W2DeviceGate;
import com.kmks.w2.domain.vo.W2DeviceGateVo;
import com.kmks.w2.domain.bo.W2DeviceGateBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 闸机设备Service接口
 *
 * @author Lynn
 * @date 2024-04-17
 */
public interface IW2DeviceGateService {

    /**
     * 查询闸机设备
     */
    W2DeviceGateVo queryById(Long gateId);

    /**
     * 查询闸机设备列表
     */
    TableDataInfo<W2DeviceGateVo> queryPageList(W2DeviceGateBo bo, PageQuery pageQuery);

    /**
     * 查询闸机设备列表
     */
    List<W2DeviceGateVo> queryList(W2DeviceGateBo bo);

    /**
     * 新增闸机设备
     */
    Boolean insertByBo(W2DeviceGateBo bo);

    /**
     * 修改闸机设备
     */
    Boolean updateByBo(W2DeviceGateBo bo);

    /**
     * 校验并批量删除闸机设备信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
