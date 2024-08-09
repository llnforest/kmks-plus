package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2CheckcarBo;
import com.kmks.w2.domain.vo.W2CheckcarVo;
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
import com.kmks.w2.service.IW2CheckcarService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车辆自检
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/checkcar")
public class W2CheckcarController extends BaseController {

    private final IW2CheckcarService iW2CheckcarService;

    /**
     * 查询车辆自检列表
     */
    @SaCheckPermission("w2:checkcar:list")
    @GetMapping("/list")
    public TableDataInfo<W2CheckcarVo> list(W2CheckcarBo bo, PageQuery pageQuery) {
        return iW2CheckcarService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车辆自检列表
     */
    @SaCheckPermission("w2:checkcar:export")
    @Log(title = "车辆自检", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2CheckcarBo bo, HttpServletResponse response) {
        List<W2CheckcarVo> list = iW2CheckcarService.queryList(bo);
        ExcelUtil.exportExcel(list, "车辆自检", W2CheckcarVo.class, response);
    }

    /**
     * 获取车辆自检详细信息
     *
     * @param sCarno 主键
     */
    @SaCheckPermission("w2:checkcar:query")
    @GetMapping("/{sCarno}")
    public R<W2CheckcarVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String sCarno) {
        return R.ok(iW2CheckcarService.queryById(sCarno));
    }

    /**
     * 新增车辆自检
     */
    @SaCheckPermission("w2:checkcar:add")
    @Log(title = "车辆自检", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2CheckcarBo bo) {
        return toAjax(iW2CheckcarService.insertByBo(bo));
    }

    /**
     * 修改车辆自检
     */
    @SaCheckPermission("w2:checkcar:edit")
    @Log(title = "车辆自检", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2CheckcarBo bo) {
        return toAjax(iW2CheckcarService.updateByBo(bo));
    }

    /**
     * 删除车辆自检
     *
     * @param sCarnos 主键串
     */
    @SaCheckPermission("w2:checkcar:remove")
    @Log(title = "车辆自检", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sCarnos}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] sCarnos) {
        return toAjax(iW2CheckcarService.deleteWithValidByIds(Arrays.asList(sCarnos), true));
    }
}
