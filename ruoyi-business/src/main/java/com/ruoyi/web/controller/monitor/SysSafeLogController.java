package com.ruoyi.web.controller.monitor;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.system.domain.vo.SysSafeLogStatVo;
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
import com.ruoyi.system.domain.vo.SysSafeLogVo;
import com.ruoyi.system.domain.bo.SysSafeLogBo;
import com.ruoyi.system.service.ISysSafeLogService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全日志
 *
 * @author ghgd
 * @date 2023-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/safeLog")
public class SysSafeLogController extends BaseController {

    private final ISysSafeLogService iSysSafeLogService;

    /**
     * 查询安全日志列表
     */
    @SaCheckPermission("monitor:safeLog:list")
    @Log(title = "安全日志", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<SysSafeLogVo> list(SysSafeLogBo bo, PageQuery pageQuery) {
        return iSysSafeLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出安全日志列表
     */
    @SaCheckPermission("monitor:safeLog:export")
    @Log(title = "安全日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysSafeLogBo bo, HttpServletResponse response) {
        List<SysSafeLogVo> list = iSysSafeLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "安全日志", SysSafeLogVo.class, response);
    }

    /**
     * 导出统计日志
     */
    @SaCheckPermission("monitor:safeLog:export")
    @Log(title = "统计日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export/state")
    public void exportStat( HttpServletResponse response) {
        List<SysSafeLogStatVo> list = iSysSafeLogService.queryListState();
        ExcelUtil.exportExcel(list, "统计日志", SysSafeLogStatVo.class, response);
    }

    /**
     * 获取安全日志详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("monitor:safeLog:query")
    @GetMapping("/{id}")
    public R<SysSafeLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iSysSafeLogService.queryById(id));
    }

    /**
     * 新增安全日志
     */
    @SaCheckPermission("monitor:safeLog:add")
    @Log(title = "安全日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysSafeLogBo bo) {
        return toAjax(iSysSafeLogService.insertByBo(bo));
    }

    /**
     * 修改安全日志
     */
    @SaCheckPermission("monitor:safeLog:edit")
    @Log(title = "安全日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysSafeLogBo bo) {
        return toAjax(iSysSafeLogService.updateByBo(bo));
    }

    /**
     * 删除安全日志
     *
     * @param ids 主键串
     */
    @SaCheckPermission("monitor:safeLog:remove")
    @Log(title = "安全日志", businessType = BusinessType.DELETE, permission = "monitor:safeLog:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iSysSafeLogService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
