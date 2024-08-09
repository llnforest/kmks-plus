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
import com.kmks.w2.domain.vo.W2DeviceGateVo;
import com.kmks.w2.domain.bo.W2DeviceGateBo;
import com.kmks.w2.service.IW2DeviceGateService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 闸机设备
 *
 * @author Lynn
 * @date 2024-04-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/deviceGate")
public class W2DeviceGateController extends BaseController {

    private final IW2DeviceGateService iW2DeviceGateService;

    /**
     * 查询闸机设备列表
     */
    @SaCheckPermission("w2:deviceGate:list")
    @Log(title = "闸机设备", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<W2DeviceGateVo> list(W2DeviceGateBo bo, PageQuery pageQuery) {
        return iW2DeviceGateService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出闸机设备列表
     */
    @SaCheckPermission("w2:deviceGate:export")
    @Log(title = "闸机设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2DeviceGateBo bo, HttpServletResponse response) {
        List<W2DeviceGateVo> list = iW2DeviceGateService.queryList(bo);
        ExcelUtil.exportExcel(list, "闸机设备", W2DeviceGateVo.class, response);
    }

    /**
     * 获取闸机设备详细信息
     *
     * @param gateId 主键
     */
    @SaCheckPermission("w2:deviceGate:query")
    @Log(title = "闸机设备", businessType = BusinessType.QUERY)
    @GetMapping("/{gateId}")
    public R<W2DeviceGateVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long gateId) {
        return R.ok(iW2DeviceGateService.queryById(gateId));
    }

    /**
     * 新增闸机设备
     */
    @SaCheckPermission("w2:deviceGate:add")
    @Log(title = "闸机设备", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2DeviceGateBo bo) {
        return toAjax(iW2DeviceGateService.insertByBo(bo));
    }

    /**
     * 修改闸机设备
     */
    @SaCheckPermission("w2:deviceGate:edit")
    @Log(title = "闸机设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2DeviceGateBo bo) {
        return toAjax(iW2DeviceGateService.updateByBo(bo));
    }

    /**
     * 删除闸机设备
     *
     * @param gateIds 主键串
     */
    @SaCheckPermission("w2:deviceGate:remove")
    @Log(title = "闸机设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gateIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] gateIds) {
        return toAjax(iW2DeviceGateService.deleteWithValidByIds(Arrays.asList(gateIds), true));
    }
}
