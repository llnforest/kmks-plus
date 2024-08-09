package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.service.IW2KcxxService;
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
 * 过程明细
 *
 * @author lynn
 * @date 2023-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/kcxx")
public class W2KcxxController extends BaseController {

    private final IW2KcxxService iW2KcxxService;

    /**
     * 查询过程明细列表
     */
    @SaCheckPermission("w2:kcxx:list")
    @GetMapping("/list")
    public TableDataInfo<W2KcxxVo> list(W2KcxxBo bo, PageQuery pageQuery) {
        return iW2KcxxService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询过程明细列表
     */
    @PostMapping("/carList")
    public R<List<W2KcxxVo>> carList(@RequestBody W2KcxxBo bo) {
        return R.ok(iW2KcxxService.queryList(bo));
    }

    /**
     * 导出过程明细列表
     */
    @SaCheckPermission("w2:kcxx:export")
    @Log(title = "过程明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KcxxBo bo, HttpServletResponse response) {
        List<W2KcxxVo> list = iW2KcxxService.queryList(bo);
        ExcelUtil.exportExcel(list, "过程明细", W2KcxxVo.class, response);
    }

    /**
     * 获取过程明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:kcxx:query")
    @GetMapping("/{id}")
    public R<W2KcxxVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2KcxxService.queryById(id));
    }

    /**
     * 新增过程明细
     */
    @SaCheckPermission("w2:kcxx:add")
    @Log(title = "过程明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KcxxBo bo) {
        return toAjax(iW2KcxxService.insertByBo(bo));
    }

    /**
     * 修改过程明细
     */
    @SaCheckPermission("w2:kcxx:edit")
    @Log(title = "过程明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KcxxBo bo) {
        return toAjax(iW2KcxxService.updateByBo(bo));
    }

    /**
     * 删除过程明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:kcxx:remove")
    @Log(title = "过程明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2KcxxService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
