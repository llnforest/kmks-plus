package com.kmks.w2.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kmks.w2.domain.W2Flowlog;
import com.kmks.w2.domain.vo.AnalyseKfdmVo;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 考试过程信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-24
 */
public interface W2FlowlogMapper extends BaseMapperPlus<W2FlowlogMapper, W2Flowlog, W2FlowVo> {
    List<W2FlowVo> getFlowListByDay(@Param("ksbh") String ksbh,@Param("ksrq") String ksrq);

    List<AnalyseKfdmVo> getAnalyseKfdmList( @Param("ew") LambdaQueryWrapper ew);

    @Select("SELECT SUM(kscs) FROM W2_FLOWLOG")
    Integer getSumKscs();
}
