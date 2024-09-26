package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.service.IW2QueuingService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Star
 * @description: 考车
 * @date 2023/3/31 11:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/w2/kcdd")
public class W2KcddController extends BaseController {
    private final IW2QueuingService iw2QueuingService;

    /**
     * 查询考车列表
     */
    @PostMapping("/carList")
    public R<List<W2KcxxVo>> carList(@RequestBody W2KcxxBo bo) {
        return R.ok(iw2QueuingService.queryCarList(bo));
    }

    /**
     * 查询约考列表
     */
    @SaCheckPermission("w2:kcdd:aboutList")
    @GetMapping("/aboutList")
    public TableDataInfo<W2QueuingVo> aboutList(W2QueuingBo bo, PageQuery pageQuery){
        return iw2QueuingService.queryPageListByAbout(bo,pageQuery);
    }

    /**
     * 分车
     */
    @SaCheckPermission("w2:kcdd:choice")
    @PutMapping("/choice")
    public R<Void> choice(@RequestBody W2QueuingBo bo){
        return toAjax(iw2QueuingService.updateByBo(bo));
    }

    /**
     * 查询任务调度列表
     */
    @SaCheckPermission("w2:kcdd:taskList")
    @GetMapping("/taskList")
    public TableDataInfo<W2QueuingVo> taskList(W2QueuingBo bo,PageQuery pageQuery){
        return iw2QueuingService.queryPageListByTask(bo,pageQuery);
    }
    /**
     *  换车
     */
    @SaCheckPermission("w2:kcdd:change")
    @PutMapping("/change")
    public R<Void> change(@RequestBody W2QueuingBo bo){
        return toAjax(iw2QueuingService.changeByBo(bo));
    }

    /**
     *  随机换车
     */
    @SaCheckPermission("w2:kcdd:randomChange")
    @PutMapping("/randomChange")
    public R<Void> randomChange(@RequestBody W2QueuingBo bo){
        return toAjax(iw2QueuingService.randomChangeByBo(bo));
    }

    /**
     *  上下移动
     */
    @SaCheckPermission("w2:kcdd:upDown")
    @PutMapping("/upDown")
    public R<Void> upDown(@RequestParam("newIndex") Long newIndex,
                          @RequestParam("oldIndex") Long oldIndex,
                          @RequestParam("id") Long id
                          ){
        return toAjax(iw2QueuingService.upDownBdxh(newIndex,oldIndex,id));
    }

    /**
     *  取消考试
     */
    @SaCheckPermission("w2:kcdd:cancel")
    @PutMapping("/cancel/{ids}")
    public R<Void> cancel(@NotEmpty(message = "主键不能为空") @PathVariable("ids") String ids){
        return toAjax(iw2QueuingService.cancelExam(ids));
    }

}
