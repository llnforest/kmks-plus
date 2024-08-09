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
import com.kmks.w2.domain.vo.W2DeviceSignVo;
import com.kmks.w2.domain.bo.W2DeviceSignBo;
import com.kmks.w2.service.IW2DeviceSignService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 签到设备
 *
 * @author lynn
 * @date 2024-04-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/deviceSign")
public class W2DeviceSignController extends BaseController {

    private final IW2DeviceSignService iW2DeviceSignService;

    /**
     * 查询签到设备列表
     */
    @SaCheckPermission("w2:deviceSign:list")
    @Log(title = "签到设备", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<W2DeviceSignVo> list(W2DeviceSignBo bo, PageQuery pageQuery) {
        return iW2DeviceSignService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出签到设备列表
     */
    @SaCheckPermission("w2:deviceSign:export")
    @Log(title = "签到设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2DeviceSignBo bo, HttpServletResponse response) {
        List<W2DeviceSignVo> list = iW2DeviceSignService.queryList(bo);
        ExcelUtil.exportExcel(list, "签到设备", W2DeviceSignVo.class, response);
    }

    /**
     * 获取签到设备详细信息
     *
     * @param signId 主键
     */
    @SaCheckPermission("w2:deviceSign:query")
    @Log(title = "签到设备", businessType = BusinessType.QUERY)
    @GetMapping("/{signId}")
    public R<W2DeviceSignVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long signId) {
        return R.ok(iW2DeviceSignService.queryById(signId));
    }

    /**
     * 新增签到设备
     */
    @SaCheckPermission("w2:deviceSign:add")
    @Log(title = "签到设备", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2DeviceSignBo bo) {
        return toAjax(iW2DeviceSignService.insertByBo(bo));
    }

    /**
     * 修改签到设备
     */
    @SaCheckPermission("w2:deviceSign:edit")
    @Log(title = "签到设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2DeviceSignBo bo) {
        return toAjax(iW2DeviceSignService.updateByBo(bo));
    }

    /**
     * 删除签到设备
     *
     * @param signIds 主键串
     */
    @SaCheckPermission("w2:deviceSign:remove")
    @Log(title = "签到设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{signIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] signIds) {
        return toAjax(iW2DeviceSignService.deleteWithValidByIds(Arrays.asList(signIds), true));
    }
}
