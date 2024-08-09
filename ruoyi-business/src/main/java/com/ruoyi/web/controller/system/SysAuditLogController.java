package com.ruoyi.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.SysAuditLogBo;
import com.ruoyi.system.domain.vo.AuditLogAnalyseDto;
import com.ruoyi.system.domain.vo.SysAuditLogVo;
import com.ruoyi.system.service.ISysAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 审计日志
 *
 * @author Lynn
 * @date 2024-07-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/auditLog")
public class SysAuditLogController extends BaseController {

    private final ISysAuditLogService iSysAuditLogService;

    /**
     * 查询审计日志列表
     */
    @SaCheckPermission("system:auditLog:list")
    @GetMapping("/list")
    public TableDataInfo<SysAuditLogVo> list(SysAuditLogBo bo, PageQuery pageQuery) {
        return iSysAuditLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出审计日志列表
     */
    @SaCheckPermission("system:auditLog:export")
    @Log(title = "审计日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysAuditLogBo bo, HttpServletResponse response) {
        List<SysAuditLogVo> list = iSysAuditLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "审计日志", SysAuditLogVo.class, response);
    }

    /**
     * 导出审计日志统计
     */
    @SaCheckPermission("system:auditLog:exportAnalyse")
    @Log(title = "审计日志", businessType = BusinessType.EXPORT)
    @PostMapping("/exportAnalyse")
    public void exportAnalyse(SysAuditLogBo bo, HttpServletResponse response) {
        List<AuditLogAnalyseDto> list = iSysAuditLogService.statisticsItems();
        ExcelUtil.exportExcel(list, "审计日志统计", AuditLogAnalyseDto.class, response);
    }

    /**
     * 获取审计日志详细信息
     *
     * @param auditId 主键
     */
    @SaCheckPermission("system:auditLog:query")
    @GetMapping("/{auditId}")
    public R<SysAuditLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long auditId) {
        return R.ok(iSysAuditLogService.queryById(auditId));
    }

    /**
     * 新增审计日志
     */
    @SaCheckPermission("system:auditLog:add")
    @Log(title = "审计日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysAuditLogBo bo) {
        return toAjax(iSysAuditLogService.insertByBo(bo));
    }

    /**
     * 修改审计日志
     */
    @SaCheckPermission("system:auditLog:edit")
    @Log(title = "审计日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysAuditLogBo bo) {
        return toAjax(iSysAuditLogService.updateByBo(bo));
    }

    /**
     * 删除审计日志
     *
     * @param auditIds 主键串
     */
    @SaCheckPermission("system:auditLog:remove")
    @Log(title = "审计日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{auditIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] auditIds) {
        return toAjax(iSysAuditLogService.deleteWithValidByIds(Arrays.asList(auditIds), true));
    }
}
