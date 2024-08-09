package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.kmks.w2.domain.bo.AnalyseKfdmBo;
import com.kmks.w2.domain.vo.AnalyseKfdmVo;
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
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.service.IW2FlowlogService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试过程信息
 *
 * @author ruoyi
 * @date 2024-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/flow")
public class W2FlowController extends BaseController {

    private final IW2FlowlogService iW2FlowlogService;

    /**
     * 查询评判项统计
     */
    @SaCheckPermission("w2:flow:analyseKfxm")
    @Log(title = "考试数据管理", businessType = BusinessType.QUERY,remark = "查询评判项统计")
    @GetMapping("/analyseKfxm")
    public List<AnalyseKfdmVo> analyseKfxm(AnalyseKfdmBo bo) {
        return iW2FlowlogService.getAnalyseKfxmVoList(bo);
    }

    /**
     * 查询考试过程信息列表
     */
    @SaCheckPermission("w2:flowlog:list")
    @GetMapping("/list")
    public TableDataInfo<W2FlowVo> list(W2FlowBo bo, PageQuery pageQuery) {
        return iW2FlowlogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考试过程信息列表
     */
    @SaCheckPermission("w2:flowlog:export")
    @Log(title = "考试过程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2FlowBo bo, HttpServletResponse response) {
        List<W2FlowVo> list = iW2FlowlogService.queryList(bo);
        ExcelUtil.exportExcel(list, "考试过程信息", W2FlowVo.class, response);
    }

    /**
     * 获取考试过程信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:flowlog:query")
    @GetMapping("/{id}")
    public R<W2FlowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2FlowlogService.queryById(id));
    }

    /**
     * 新增考试过程信息
     */
    @SaCheckPermission("w2:flowlog:add")
    @Log(title = "考试过程信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2FlowBo bo) {
        return toAjax(iW2FlowlogService.insertByBo(bo));
    }

    /**
     * 修改考试过程信息
     */
    @SaCheckPermission("w2:flowlog:edit")
    @Log(title = "考试过程信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2FlowBo bo) {
        return toAjax(iW2FlowlogService.updateByBo(bo));
    }

    /**
     * 删除考试过程信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:flowlog:remove")
    @Log(title = "考试过程信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2FlowlogService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
