package com.kmks.w2.controller;

import java.io.*;
import java.util.List;
import java.util.Arrays;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.extern.slf4j.Slf4j;
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
import com.kmks.w2.domain.vo.W2ConfigCarVo;
import com.kmks.w2.domain.bo.W2ConfigCarBo;
import com.kmks.w2.service.IW2ConfigCarService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 车辆合码器
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/configCar")
@Slf4j
public class W2ConfigCarController extends BaseController {

    private final IW2ConfigCarService iW2ConfigCarService;

    /**
     * 查询车辆合码器列表
     */
    @SaCheckPermission("w2:configCar:list")
    @GetMapping("/list")
    public TableDataInfo<W2ConfigCarVo> list(W2ConfigCarBo bo, PageQuery pageQuery) {
        return iW2ConfigCarService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车辆合码器列表
     */
    @SaCheckPermission("w2:configCar:export")
    @Log(title = "车辆合码器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2ConfigCarBo bo, HttpServletResponse response) {
        List<W2ConfigCarVo> list = iW2ConfigCarService.queryList(bo);
        ExcelUtil.exportExcel(list, "车辆合码器", W2ConfigCarVo.class, response);
    }

    /**
     * 获取车辆合码器详细信息
     *
     * @param carno 主键
     */
    @SaCheckPermission("w2:configCar:query")
    @GetMapping("/{carno}")
    public R<W2ConfigCarVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String carno) {
        return R.ok(iW2ConfigCarService.queryById(carno));
    }

    /**
     * 新增车辆合码器
     */
    @SaCheckPermission("w2:configCar:add")
    @Log(title = "车辆合码器", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2ConfigCarBo bo) {
        return toAjax(iW2ConfigCarService.insertByBo(bo));
    }

    /**
     * 修改车辆合码器
     */
    @SaCheckPermission("w2:configCar:edit")
    @Log(title = "车辆合码器", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2ConfigCarBo bo) {
        return toAjax(iW2ConfigCarService.updateByBo(bo));
    }

    /**
     * 删除车辆合码器
     *
     * @param carnos 主键串
     */
    @SaCheckPermission("w2:configCar:remove")
    @Log(title = "车辆合码器", businessType = BusinessType.DELETE)
    @DeleteMapping("/{carnos}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] carnos) {
        return toAjax(iW2ConfigCarService.deleteWithValidByIds(Arrays.asList(carnos), true));
    }

    @SaCheckPermission("w2:configCar:import")
    @Log(title = "导入车辆合码器信息", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public R<Void> importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), W2ConfigCarVo.class, new PageReadListener<W2ConfigCarVo>(
                    iW2ConfigCarService::insertBatch
                ))
                .headRowNumber(1)
                .sheet().doRead();
        return R.ok("导入成功");
    }

}
