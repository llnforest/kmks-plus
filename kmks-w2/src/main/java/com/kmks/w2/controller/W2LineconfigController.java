package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2LineconfigBo;
import com.kmks.w2.domain.vo.W2LineconfigVo;
import com.kmks.w2.service.IW2LineconfigService;
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
 * 路线管理
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/lineconfig")
public class W2LineconfigController extends BaseController {

    private final IW2LineconfigService iW2LineconfigService;

    /**
     * 查询路线管理列表
     */
    @SaCheckPermission("w2:lineconfig:list")
    @GetMapping("/list")
    public TableDataInfo<W2LineconfigVo> list(W2LineconfigBo bo, PageQuery pageQuery) {
        return iW2LineconfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出路线管理列表
     */
    @SaCheckPermission("w2:lineconfig:export")
    @Log(title = "路线管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2LineconfigBo bo, HttpServletResponse response) {
        List<W2LineconfigVo> list = iW2LineconfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "路线管理", W2LineconfigVo.class, response);
    }

    /**
     * 获取路线管理详细信息
     *
     * @param line 主键
     */
    @SaCheckPermission("w2:lineconfig:query")
    @GetMapping("/{line}")
    public R<W2LineconfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long line) {
        return R.ok(iW2LineconfigService.queryById(line));
    }

    /**
     * 新增路线管理
     */
    @SaCheckPermission("w2:lineconfig:add")
    @Log(title = "路线管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2LineconfigBo bo) {
        return toAjax(iW2LineconfigService.insertByBo(bo));
    }

    /**
     * 修改路线管理
     */
    @SaCheckPermission("w2:lineconfig:edit")
    @Log(title = "路线管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2LineconfigBo bo) {
        return toAjax(iW2LineconfigService.updateByBo(bo));
    }

    /**
     * 删除路线管理
     *
     * @param lines 主键串
     */
    @SaCheckPermission("w2:lineconfig:remove")
    @Log(title = "路线管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lines}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] lines) {
        return toAjax(iW2LineconfigService.deleteWithValidByIds(Arrays.asList(lines), true));
    }


    /**
     * 选择查找
     * @param bo
     */
    @PostMapping("/select")
    public R<List<W2LineconfigVo>> select(W2LineconfigBo bo){
        return R.ok(iW2LineconfigService.queryList(bo));
    }
}
