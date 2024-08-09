package com.kmks.w2.mapper;

import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.W2Queuing;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 排队信息Mapper接口
 *
 * @author Lynn
 * @date 2023-03-28
 */
public interface W2QueuingMapper extends BaseMapperPlus<W2QueuingMapper, W2Queuing, W2QueuingVo> {

    List<W2Queuing> selectCarPersonList();

    @Select("select max(bdxh) from w2_queuing")
    Long getMaxBdxh();

}
