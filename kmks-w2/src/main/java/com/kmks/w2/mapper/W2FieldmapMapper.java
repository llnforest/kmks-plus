package com.kmks.w2.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.W2Fieldmap;
import com.kmks.w2.domain.vo.W2FieldmapVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

/**
 * 地图模型Mapper接口
 *
 * @author ghgd
 * @date 2023-03-15
 */
@DS("param")
public interface W2FieldmapMapper extends BaseMapperPlus<W2FieldmapMapper, W2Fieldmap, W2FieldmapVo> {

}
