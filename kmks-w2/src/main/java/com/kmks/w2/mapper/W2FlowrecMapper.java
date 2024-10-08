package com.kmks.w2.mapper;


import com.kmks.w2.domain.W2Flowrec;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考试过程信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-24
 */
public interface W2FlowrecMapper extends BaseMapperPlus<W2FlowrecMapper, W2Flowrec, W2FlowVo> {
    List<W2FlowVo> getFlowListByDay(@Param("zjhm") String zjhm, @Param("ksrq") String ksrq);
}
