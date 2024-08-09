package com.kmks.w2.controller;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
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
import com.kmks.w2.domain.vo.W2ConfigDeviceVo;
import com.kmks.w2.domain.bo.W2ConfigDeviceBo;
import com.kmks.w2.service.IW2ConfigDeviceService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备合码器
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/configDevice")
public class W2ConfigDeviceController extends BaseController {

    private final IW2ConfigDeviceService iW2ConfigDeviceService;

    /**
     * 查询设备合码器列表
     */
    @SaCheckPermission("w2:configDevice:list")
    @GetMapping("/list")
    public TableDataInfo<W2ConfigDeviceVo> list(W2ConfigDeviceBo bo, PageQuery pageQuery) {
        return iW2ConfigDeviceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备合码器列表
     */
    @SaCheckPermission("w2:configDevice:export")
    @Log(title = "设备合码器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2ConfigDeviceBo bo, HttpServletResponse response) {
        List<W2ConfigDeviceVo> list = iW2ConfigDeviceService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备合码器", W2ConfigDeviceVo.class, response);
    }

    /**
     * 获取设备合码器详细信息
     *
     * @param deviceno 主键
     */
    @SaCheckPermission("w2:configDevice:query")
    @GetMapping("/{deviceno}")
    public R<W2ConfigDeviceVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long deviceno) {
        return R.ok(iW2ConfigDeviceService.queryById(deviceno));
    }

    /**
     * 新增设备合码器
     */
    @SaCheckPermission("w2:configDevice:add")
    @Log(title = "设备合码器", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2ConfigDeviceBo bo) {
        return toAjax(iW2ConfigDeviceService.insertByBo(bo));
    }

    /**
     * 修改设备合码器
     */
    @SaCheckPermission("w2:configDevice:edit")
    @Log(title = "设备合码器", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2ConfigDeviceBo bo) {
        return toAjax(iW2ConfigDeviceService.updateByBo(bo));
    }

    /**
     * 删除设备合码器
     *
     * @param devicenos 主键串
     */
    @SaCheckPermission("w2:configDevice:remove")
    @Log(title = "设备合码器", businessType = BusinessType.DELETE)
    @DeleteMapping("/{devicenos}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] devicenos) {
        return toAjax(iW2ConfigDeviceService.deleteWithValidByIds(Arrays.asList(devicenos), true));
    }
    @SaCheckPermission("w2:configDevice:import")
    @Log(title = "导入设备合码器信息", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public R<Void> importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), W2ConfigDeviceVo.class, new PageReadListener<W2ConfigDeviceVo>(
                        iW2ConfigDeviceService::insertBatch
                ))
                .headRowNumber(1)
                .sheet().doRead();
        return R.ok("导入成功");
    }
}
