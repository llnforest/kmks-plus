package com.kmks.w2.service;

import com.kmks.w2.domain.W2Carsignal;
import com.kmks.w2.domain.vo.W2CarsignalVo;
import com.kmks.w2.domain.bo.W2CarsignalBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 车辆信号Service接口
 *
 * @author Lynn
 * @date 2024-05-29
 */
public interface IW2CarsignalService {

    /**
     * 查询车辆信号
     */
    W2CarsignalVo queryById(Long id);

    /**
     * 查询车辆信号列表
     */
    TableDataInfo<W2CarsignalVo> queryPageList(W2CarsignalBo bo, PageQuery pageQuery);

    /**
     * 查询车辆信号列表
     */
    List<W2CarsignalVo> queryList(W2CarsignalBo bo);

    /**
     * 新增车辆信号
     */
    Boolean insertByBo(W2CarsignalBo bo);

    /**
     * 修改车辆信号
     */
    Boolean updateByBo(W2CarsignalBo bo);

    /**
     * 校验并批量删除车辆信号信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
