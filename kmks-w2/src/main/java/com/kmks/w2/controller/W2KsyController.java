package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.service.WebService;
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
import com.kmks.w2.domain.vo.W2KsyVo;
import com.kmks.w2.domain.bo.W2KsyBo;
import com.kmks.w2.service.IW2KsyService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考官信息
 *
 * @author lynn
 * @date 2023-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/ksy")
public class W2KsyController extends BaseController {

    private final IW2KsyService iW2KsyService;
    private final WebService webService;

    /**
     * 查询考官信息列表
     */
    @SaCheckPermission("w2:ksy:list")
    @GetMapping("/list")
    public TableDataInfo<W2KsyVo> list(W2KsyBo bo, PageQuery pageQuery) {
        return iW2KsyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考官信息列表
     */
    @SaCheckPermission("w2:ksy:export")
    @Log(title = "考官信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KsyBo bo, HttpServletResponse response) {
        List<W2KsyVo> list = iW2KsyService.queryList(bo);
        ExcelUtil.exportExcel(list, "考官信息", W2KsyVo.class, response);
    }

    /**
     * 获取考官信息详细信息
     *
     * @param xh 主键
     */
    @SaCheckPermission("w2:ksy:query")
    @GetMapping("/{xh}")
    public R<W2KsyVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String xh) {
        return R.ok(iW2KsyService.queryById(xh));
    }

    /**
     * 新增考官信息
     */
    @SaCheckPermission("w2:ksy:add")
    @Log(title = "考官信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KsyBo bo) {
        return toAjax(iW2KsyService.insertByBo(bo));
    }

    /**
     * 修改考官信息
     */
    @SaCheckPermission("w2:ksy:edit")
    @Log(title = "考官信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KsyBo bo) {
        return toAjax(iW2KsyService.updateByBo(bo));
    }

    /**
     * 删除考官信息
     *
     * @param xhs 主键串
     */
    @SaCheckPermission("w2:ksy:remove")
    @Log(title = "考官信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xhs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] xhs) {
        return toAjax(iW2KsyService.deleteWithValidByIds(Arrays.asList(xhs), true));
    }


}
