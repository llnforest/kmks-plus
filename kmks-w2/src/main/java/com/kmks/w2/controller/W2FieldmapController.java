package com.kmks.w2.controller;

import java.io.IOException;
import java.util.Arrays;

import com.alibaba.excel.EasyExcel;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.utils.MapExtraDataListener;
import com.kmks.w2.domain.bo.W2FieldmapBo;
import com.kmks.w2.domain.vo.W2FieldmapVo;
import com.kmks.w2.service.IW2FieldmapService;
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
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 地图模型
 *
 * @author ghgd
 * @date 2023-03-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/fieldmap")
public class W2FieldmapController extends BaseController {

    private final IW2FieldmapService iW2FieldmapService;

    /**
     * 查询地图模型列表
     */
    @SaCheckPermission("w2:fieldmap:list")
    @GetMapping("/list")
    public TableDataInfo<W2FieldmapVo> list(W2FieldmapBo bo, PageQuery pageQuery) {
        return iW2FieldmapService.queryPageList(bo, pageQuery);
    }



    /**
     * 获取地图模型详细信息
     *
     * @param fieldname 主键
     */
    @SaCheckPermission("w2:fieldmap:query")
    @GetMapping("/{fieldname}")
    public R<W2FieldmapVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String fieldname) {
        return R.ok(iW2FieldmapService.queryById(fieldname));
    }

    /**
     * 新增地图模型
     */
    @SaCheckPermission("w2:fieldmap:add")
    @Log(title = "地图模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2FieldmapBo bo) {
        return toAjax(iW2FieldmapService.insertByBo(bo));
    }

    /**
     * 修改地图模型
     */
    @SaCheckPermission("w2:fieldmap:edit")
    @Log(title = "地图模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2FieldmapBo bo) {
        return toAjax(iW2FieldmapService.updateByBo(bo));
    }

    /**
     * 删除地图模型
     *
     * @param fieldnames 主键串
     */
    @SaCheckPermission("w2:fieldmap:remove")
    @Log(title = "地图模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fieldnames}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] fieldnames) {
        return toAjax(iW2FieldmapService.deleteWithValidByIds(Arrays.asList(fieldnames), true));
    }


    @SaCheckPermission("w2:fieldmap:import")
    @Log(title = "导入地图模型",businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public R<Void> importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ExtraData.class, new MapExtraDataListener(iW2FieldmapService))
                .headRowNumber(0)
                .sheet().doRead();
        return R.ok("导入成功");
    }

    /**
     * 导出地图模型列表
     */
    @SaCheckPermission("w2:fieldmap:export")
    @Log(title = "地图模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2FieldmapBo bo, HttpServletResponse response) {
        W2FieldmapVo w2FieldmapVo = iW2FieldmapService.queryById(bo.getId());
        if (w2FieldmapVo != null) {
            iW2FieldmapService.exportFileMapModel(w2FieldmapVo,response);
        }
    }
}
