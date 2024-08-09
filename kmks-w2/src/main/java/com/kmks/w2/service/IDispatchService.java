package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.dto.DispatchCarDto;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.ruoyi.common.core.domain.R;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 15:17
 */
public interface IDispatchService {
    List<DispatchCarDto> carQueueList();

    List<DispatchCenterDto> getDispatchCenterList();

    List<W2FlowVo> getFlowList(W2FlowBo bo);
}
