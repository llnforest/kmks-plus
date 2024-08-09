package com.kmks.w2.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.W2CarModel;
import com.kmks.w2.domain.vo.W2CarModelVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

/**
 * 车辆模型Mapper接口
 *
 * @author ghgd
 * @date 2023-03-14
 */
@DS("param")
public interface W2CarModelMapper extends BaseMapperPlus<W2CarModelMapper, W2CarModel, W2CarModelVo> {

}
