package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.service.IW2QueuingService;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 排队管理
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/queuing")
public class W2QueuingController extends BaseController {

    private final IW2QueuingService iW2QueuingService;

    /**
     * 查询排队管理列表
     */
    @SaCheckPermission("w2:queuing:list")
    @Log(title = "排队管理", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<W2QueuingVo> list(W2QueuingBo bo, PageQuery pageQuery) {
        return iW2QueuingService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出排队管理列表
     */
    @SaCheckPermission("w2:queuing:export")
    @Log(title = "排队管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2QueuingBo bo, HttpServletResponse response) {
        List<W2QueuingVo> list = iW2QueuingService.queryList(bo);
        ExcelUtil.exportExcel(list, "排队管理", W2QueuingVo.class, response);
    }

    /**
     * 获取排队管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:queuing:query")
    @Log(title = "排队管理", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public R<W2QueuingVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2QueuingService.queryById(id));
    }

    /**
     * 新增排队管理
     */
    @SaCheckPermission("w2:queuing:add")
    @Log(title = "排队管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2QueuingBo bo) {
        return toAjax(iW2QueuingService.insertByBo(bo));
    }

    /**
     * 修改排队管理
     */
    @SaCheckPermission({"w2:queuing:edit","w2:queuing:kscx"})
    @Log(title = "排队管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping({"","/updateKscx","/updateInfo"})
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2QueuingBo bo) {
        return toAjax(iW2QueuingService.updateByBo(bo));
    }

    /**
     * 删除排队管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:queuing:remove")
    @Log(title = "排队管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2QueuingService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     *  上下移动
     */
    @SaCheckPermission("w2:queuing:upDown")
    @Log(title = "排队管理", businessType = BusinessType.UPDATE,remark="调整顺序")
    @PutMapping("/upDown")
    public R<Void> upDown(@RequestParam("newIndex") Long newIndex,
                          @RequestParam("oldIndex") Long oldIndex,
                          @RequestParam("id") Long id
    ){
        return toAjax(iW2QueuingService.upDownBdxh(newIndex,oldIndex,id));
    }
}
