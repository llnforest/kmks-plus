package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.dto.DispatchCarDto;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.dto.JudgeDto;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.service.IDispatchService;
import com.kmks.w2.service.IW2KcxxService;
import com.kmks.w2.service.IW2QueuingService;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Star
 * @description: 视频监管
 * @date 2023/3/31 11:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/w2/video")
public class VideoController extends BaseController {
    private final IW2KcxxService kcxxService;



    /**
     * 查询视频监管列表
     */
    @SaCheckPermission("w2:video:list")
    @Log(title = "视频监管", businessType = BusinessType.UPDATE,remark = "查询列表")
    @GetMapping("/list")
    public R<List<W2KcxxVo>> list(){
        return R.ok(kcxxService.queryList(new W2KcxxBo()));
    }

    /**
     * 下发评判
     */
    @SaCheckPermission("w2:video:downJudge")
    @Log(title = "视频监管", businessType = BusinessType.GRANT,remark = "下发评判")
    @PostMapping("/downJudge")
    public R<Void> downJudge(@RequestBody JudgeDto judgeDto){
        return toAjax(kcxxService.judge(judgeDto));
    }

    /**
     * 查询监管车辆信息
     */
    @SaCheckPermission("w2:video:info")
    @Log(title = "视频监管", businessType = BusinessType.UPDATE,remark = "查询考车在线信息")
    @GetMapping("/{kch}")
    public R<DispatchCenterDto> info(@NotNull(message = "考车号不能为空")
                                     @PathVariable String kch){
        //TODO:测试数据
        DispatchCenterDto dispatchCenterDto = new DispatchCenterDto();
        dispatchCenterDto.setKsfs(100l);
        dispatchCenterDto.setKsxm("曹宝宝");
        dispatchCenterDto.setZjhm("3412901995091092121");
        dispatchCenterDto.setDqxm("侧方停车");
        return R.ok(dispatchCenterDto);

//        return R.ok(TcpUtils.getDispatchCenterDto(kch));
    }



}
