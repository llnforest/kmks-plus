package com.kmks.w2.mapper;

import com.kmks.w2.domain.W2Config;
import com.kmks.w2.domain.vo.W2ConfigVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

/**
 * 驾考参数Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-01
 */
public interface W2ConfigMapper extends BaseMapperPlus<W2ConfigMapper, W2Config, W2ConfigVo> {

    Integer isStandard();

}
