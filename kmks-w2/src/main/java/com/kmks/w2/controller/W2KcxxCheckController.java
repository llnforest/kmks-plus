package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.kmks.w2.domain.vo.W2KcxxCheckVo;
import com.kmks.w2.domain.bo.W2KcxxCheckBo;
import com.kmks.w2.service.IW2KcxxCheckService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考车自检
 *
 * @author Lynn
 * @date 2024-06-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/kcxxCheck")
public class W2KcxxCheckController extends BaseController {

    private final IW2KcxxCheckService iW2KcxxCheckService;

    /**
     * 查询考车自检列表
     */
    @SaCheckPermission("w2:kcxxCheck:list")
    @GetMapping("/list")
    public TableDataInfo<W2KcxxCheckVo> list(W2KcxxCheckBo bo, PageQuery pageQuery) {
        return iW2KcxxCheckService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考车自检列表
     */
    @SaCheckPermission("w2:kcxxCheck:export")
    @Log(title = "考车自检", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KcxxCheckBo bo, HttpServletResponse response) {
        List<W2KcxxCheckVo> list = iW2KcxxCheckService.queryList(bo);
        ExcelUtil.exportExcel(list, "考车自检", W2KcxxCheckVo.class, response);
    }

    /**
     * 获取考车自检详细信息
     *
     * @param kcbh 主键
     */
    @SaCheckPermission("w2:kcxxCheck:query")
    @GetMapping("/{kcbh}")
    public R<W2KcxxCheckVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String kcbh) {
        return R.ok(iW2KcxxCheckService.queryById(kcbh));
    }

    /**
     * 新增考车自检
     */
    @SaCheckPermission("w2:kcxxCheck:add")
    @Log(title = "考车自检", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KcxxCheckBo bo) {
        return toAjax(iW2KcxxCheckService.insertByBo(bo));
    }

    /**
     * 修改考车自检
     */
    @SaCheckPermission("w2:kcxxCheck:edit")
    @Log(title = "考车自检", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KcxxCheckBo bo) {
        return toAjax(iW2KcxxCheckService.updateByBo(bo));
    }

    /**
     * 删除考车自检
     *
     * @param kcbhs 主键串
     */
    @SaCheckPermission("w2:kcxxCheck:remove")
    @Log(title = "考车自检", businessType = BusinessType.DELETE)
    @DeleteMapping("/{kcbhs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] kcbhs) {
        return toAjax(iW2KcxxCheckService.deleteWithValidByIds(Arrays.asList(kcbhs), true));
    }
}
