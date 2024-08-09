package com.kmks.w2.mapper;

import com.kmks.w2.domain.W2System;
import com.kmks.w2.domain.vo.W2SystemVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.springframework.data.repository.query.Param;

/**
 * 基础编码Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-27
 */
public interface W2SystemMapper extends BaseMapperPlus<W2SystemMapper, W2System, W2SystemVo> {

    W2SystemVo selectVoByIdAndType(@Param("nid") Long nid);

    int updateVoByIdAndType(W2System w2System);
}
