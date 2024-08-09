package com.kmks.w2.mapper;
import com.kmks.w2.domain.bo.W2GpscontentBo;

import com.kmks.w2.domain.W2Gpscontent;
import com.kmks.w2.domain.vo.W2GpscontentVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 轨迹数据Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-19
 */
public interface W2GpscontentMapper extends BaseMapperPlus<W2GpscontentMapper, W2Gpscontent, W2GpscontentVo> {

    List<W2Gpscontent> selectListByTable(W2GpscontentBo bo);

}
