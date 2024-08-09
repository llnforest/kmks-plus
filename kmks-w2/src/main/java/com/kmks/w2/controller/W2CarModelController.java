package com.kmks.w2.controller;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import com.alibaba.excel.EasyExcel;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.utils.CarExtraDataListener;

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
import com.kmks.w2.domain.vo.W2CarModelVo;
import com.kmks.w2.domain.bo.W2CarModelBo;
import com.kmks.w2.service.IW2CarModelService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 车辆模型
 *
 * @author ghgd
 * @date 2023-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/carModel")
public class W2CarModelController extends BaseController {

    private final IW2CarModelService iW2CarmodelService;

    /**
     * 查询车辆模型列表
     */
    @SaCheckPermission("w2:carModel:list")
    @Log(title = "车辆模型", businessType = BusinessType.QUERY,remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<W2CarModelVo> list(W2CarModelBo bo, PageQuery pageQuery) {
        return iW2CarmodelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车辆模型列表
     */
    @SaCheckPermission("w2:carModel:export")
    @Log(title = "车辆模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2CarModelBo bo, HttpServletResponse response) {
        W2CarModelVo w2CarModelVo = iW2CarmodelService.queryById(bo.getId());
        if (w2CarModelVo != null) {
            iW2CarmodelService.exportCarModel(w2CarModelVo,response);
        }
    }


    /**
     * 获取车辆模型详细信息
     *
     * @param modelname 主键
     */
    @SaCheckPermission("w2:carModel:query")
    @Log(title = "车辆模型", businessType = BusinessType.QUERY)
    @GetMapping("/{modelname}")
    public R<W2CarModelVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable String modelname) {
        return R.ok(iW2CarmodelService.queryById(modelname));
    }

    /**
     * 新增车辆模型
     */
    @SaCheckPermission("w2:carModel:add")
    @Log(title = "车辆模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2CarModelBo bo) {
        return toAjax(iW2CarmodelService.insertByBo(bo));
    }

    /**
     * 修改车辆模型
     */
    @SaCheckPermission("w2:carModel:edit")
    @Log(title = "车辆模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2CarModelBo bo) {
        return toAjax(iW2CarmodelService.updateByBo(bo));
    }

    /**
     * 删除车辆模型
     *
     * @param modelnames 主键串
     */
    @SaCheckPermission("w2:carModel:remove")
    @Log(title = "车辆模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelnames}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] modelnames) {
        return toAjax(iW2CarmodelService.deleteWithValidByIds(Arrays.asList(modelnames), true));
    }

    @SaCheckPermission("w2:carModel:import")
    @Log(title = "导入车辆模型", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public R<Void> importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ExtraData.class, new CarExtraDataListener(iW2CarmodelService))
                .headRowNumber(0)
                .sheet().doRead();
        return R.ok("导入成功");
    }


    @PostMapping("/select")
    public R<List<W2CarModelVo>> select(W2CarModelBo bo) {
        return R.ok(iW2CarmodelService.queryList(bo));
    }

}
