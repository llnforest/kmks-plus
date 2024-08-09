package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.dto.DispatchCarDto;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.service.IDispatchService;
import com.kmks.w2.service.IW2QueuingService;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: 调度
 * @date 2023/3/31 11:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/w2/dispatch")
public class DispatchCenterController extends BaseController {
    private final IDispatchService dispatchService;

    private final IW2QueuingService iw2QueuingService;

    /**
     * 查询考车排队列表
     */
    @SaCheckPermission("w2:dispatch:carQueueList")
    @Log(title = "调度中心", businessType = BusinessType.QUERY,remark = "查询考车列表")
    @GetMapping("/carQueueList")
    public R<List<DispatchCarDto>> carQueueList() {
        return R.ok(dispatchService.carQueueList());
    }

    /**
     * 查询任务调度列表
     */
    @SaCheckPermission("w2:dispatch:centerList")
    @Log(title = "调度中心", businessType = BusinessType.QUERY,remark = "查询调度列表")
    @GetMapping("/centerList")
    public R<List<DispatchCenterDto>> centerList(){

        return R.ok(dispatchService.getDispatchCenterList());
    }

    /**
     * 查询考试明细列表
     */
    @SaCheckPermission("w2:dispatch:flowList")
    @Log(title = "调度中心", businessType = BusinessType.QUERY,remark = "查询明细列表")
    @GetMapping("/flowList")
    public R<List<W2FlowVo>> flowList(W2FlowBo bo){
        List<W2FlowVo> list = dispatchService.getFlowList(bo);
        return R.ok(list);
    }

    /**
     *  申请考试
     */
    @SaCheckPermission("w2:dispatch:apply")
    @Log(title = "调度中心", businessType = BusinessType.UPDATE,remark = "申请考试")
    @PutMapping("/applyExam/{ids}")
    public R<Void> apply(@NotEmpty(message = "主键不能为空") @PathVariable("ids") String ids){

        return toAjax(iw2QueuingService.applyExam(ids));
    }

    /**
     *  取消考试
     */
    @SaCheckPermission("w2:dispatch:cancel")
    @Log(title = "调度中心", businessType = BusinessType.UPDATE,remark = "取消考试")
    @PutMapping("/cancel/{ids}")
    public R<Void> cancel(@NotEmpty(message = "主键不能为空") @PathVariable("ids") String[] ids){
        return toAjax(iw2QueuingService.cancelExam(ids));
    }

}
