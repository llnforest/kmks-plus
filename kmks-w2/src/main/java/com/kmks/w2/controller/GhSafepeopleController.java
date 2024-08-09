package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.service.IGhSafepeopleService;
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
import com.kmks.w2.domain.vo.GhSafepeopleVo;
import com.kmks.w2.domain.bo.GhSafepeopleBo;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全员管理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/safepeople")
public class GhSafepeopleController extends BaseController {

    private final IGhSafepeopleService iGhSafepeopleService;

    /**
     * 查询安全员管理列表
     */
    @SaCheckPermission("w2:safepeople:list")
    @GetMapping("/list")
    public TableDataInfo<GhSafepeopleVo> list(GhSafepeopleBo bo, PageQuery pageQuery) {
        return iGhSafepeopleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出安全员管理列表
     */
    @SaCheckPermission("w2:safepeople:export")
    @Log(title = "安全员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(GhSafepeopleBo bo, HttpServletResponse response) {
        List<GhSafepeopleVo> list = iGhSafepeopleService.queryList(bo);
        ExcelUtil.exportExcel(list, "安全员管理", GhSafepeopleVo.class, response);
    }

    /**
     * 获取安全员管理详细信息
     *
     * @param sZjhm 主键
     */
    @SaCheckPermission("w2:safepeople:query")
    @GetMapping("/{sZjhm}")
    public R<GhSafepeopleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String sZjhm) {
        return R.ok(iGhSafepeopleService.queryById(sZjhm));
    }

    /**
     * 新增安全员管理
     */
    @SaCheckPermission("w2:safepeople:add")
    @Log(title = "安全员管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody GhSafepeopleBo bo) {
        return toAjax(iGhSafepeopleService.insertByBo(bo));
    }

    /**
     * 修改安全员管理
     */
    @SaCheckPermission("w2:safepeople:edit")
    @Log(title = "安全员管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GhSafepeopleBo bo) {
        return toAjax(iGhSafepeopleService.updateByBo(bo));
    }

    /**
     * 删除安全员管理
     *
     * @param sZjhms 主键串
     */
    @SaCheckPermission("w2:safepeople:remove")
    @Log(title = "安全员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sZjhms}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] sZjhms) {
        return toAjax(iGhSafepeopleService.deleteWithValidByIds(Arrays.asList(sZjhms), true));
    }
}
