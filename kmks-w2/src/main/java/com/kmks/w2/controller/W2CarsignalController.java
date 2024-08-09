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
import com.kmks.w2.domain.vo.W2CarsignalVo;
import com.kmks.w2.domain.bo.W2CarsignalBo;
import com.kmks.w2.service.IW2CarsignalService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车辆信号
 *
 * @author Lynn
 * @date 2024-05-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/carsignal")
public class W2CarsignalController extends BaseController {

    private final IW2CarsignalService iW2CarsignalService;

    /**
     * 查询车辆信号列表
     */
    @SaCheckPermission("w2:carsignal:list")
    @GetMapping("/list")
    public TableDataInfo<W2CarsignalVo> list(W2CarsignalBo bo, PageQuery pageQuery) {
        return iW2CarsignalService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车辆信号列表
     */
    @SaCheckPermission("w2:carsignal:export")
    @Log(title = "车辆信号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2CarsignalBo bo, HttpServletResponse response) {
        List<W2CarsignalVo> list = iW2CarsignalService.queryList(bo);
        ExcelUtil.exportExcel(list, "车辆信号", W2CarsignalVo.class, response);
    }

    /**
     * 获取车辆信号详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:carsignal:query")
    @GetMapping("/{id}")
    public R<W2CarsignalVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2CarsignalService.queryById(id));
    }

    /**
     * 新增车辆信号
     */
    @SaCheckPermission("w2:carsignal:add")
    @Log(title = "车辆信号", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2CarsignalBo bo) {
        return toAjax(iW2CarsignalService.insertByBo(bo));
    }

    /**
     * 修改车辆信号
     */
    @SaCheckPermission("w2:carsignal:edit")
    @Log(title = "车辆信号", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2CarsignalBo bo) {
        return toAjax(iW2CarsignalService.updateByBo(bo));
    }

    /**
     * 删除车辆信号
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:carsignal:remove")
    @Log(title = "车辆信号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2CarsignalService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
