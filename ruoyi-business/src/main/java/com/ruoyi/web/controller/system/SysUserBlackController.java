package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.helper.LoginHelper;
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
import com.ruoyi.system.domain.vo.SysUserBlackVo;
import com.ruoyi.system.domain.bo.SysUserBlackBo;
import com.ruoyi.system.service.ISysUserBlackService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 黑名单管理
 *
 * @author Lynn
 * @date 2023-03-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userBlack")
public class SysUserBlackController extends BaseController {

    private final ISysUserBlackService iSysUserBlackService;

    /**
     * 查询黑名单管理列表
     */
    @SaCheckPermission("system:userBlack:list")
    @Log(title = "黑名单管理", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<SysUserBlackVo> list(SysUserBlackBo bo, PageQuery pageQuery) {
        return iSysUserBlackService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出黑名单管理列表
     */
    @SaCheckPermission("system:userBlack:export")
    @Log(title = "黑名单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserBlackBo bo, HttpServletResponse response) {
        List<SysUserBlackVo> list = iSysUserBlackService.queryList(bo);
        ExcelUtil.exportExcel(list, "黑名单管理", SysUserBlackVo.class, response);
    }

    /**
     * 获取黑名单管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:userBlack:query")
    @Log(title = "黑名单管理", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public R<SysUserBlackVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iSysUserBlackService.queryById(id));
    }

    /**
     * 新增黑名单管理
     */
    @SaCheckPermission("system:userBlack:add")
    @Log(title = "黑名单管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserBlackBo bo) {
        return toAjax(iSysUserBlackService.insertByBo(bo));
    }

    /**
     * 修改黑名单管理
     */
    @SaCheckPermission("system:userBlack:edit")
    @Log(title = "黑名单管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserBlackBo bo) {
        return toAjax(iSysUserBlackService.updateByBo(bo));
    }

    /**
     * 删除黑名单管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:userBlack:remove")
    @Log(title = "黑名单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iSysUserBlackService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 解锁黑名单管理
     */
    @SaCheckPermission("system:userBlack:unLock")
    @Log(title = "黑名单管理", businessType = BusinessType.UPDATE,remark = "解锁黑名单")
    @RepeatSubmit()
    @PutMapping("/unLock/{ids}")
    public R<Void> unLock(@NotEmpty(message = "主键不能为空")
                              @PathVariable Long[] ids) {
        return toAjax(iSysUserBlackService.unLock(Arrays.asList(ids)));
    }
}
