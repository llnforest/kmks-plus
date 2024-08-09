package com.kmks.w2.service;

import com.kmks.w2.domain.W2DeviceSign;
import com.kmks.w2.domain.vo.W2DeviceSignVo;
import com.kmks.w2.domain.bo.W2DeviceSignBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 签到设备Service接口
 *
 * @author lynn
 * @date 2024-04-17
 */
public interface IW2DeviceSignService {

    /**
     * 查询签到设备
     */
    W2DeviceSignVo queryById(Long signId);

    /**
     * 查询签到设备列表
     */
    TableDataInfo<W2DeviceSignVo> queryPageList(W2DeviceSignBo bo, PageQuery pageQuery);

    /**
     * 查询签到设备列表
     */
    List<W2DeviceSignVo> queryList(W2DeviceSignBo bo);

    /**
     * 新增签到设备
     */
    Boolean insertByBo(W2DeviceSignBo bo);

    /**
     * 修改签到设备
     */
    Boolean updateByBo(W2DeviceSignBo bo);

    /**
     * 校验并批量删除签到设备信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
