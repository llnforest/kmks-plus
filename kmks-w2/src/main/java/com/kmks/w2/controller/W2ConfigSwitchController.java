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
import com.kmks.w2.domain.vo.W2ConfigSwitchVo;
import com.kmks.w2.domain.bo.W2ConfigSwitchBo;
import com.kmks.w2.service.IW2ConfigSwitchService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目监控
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/configSwitch")
public class W2ConfigSwitchController extends BaseController {

    private final IW2ConfigSwitchService iW2ConfigSwitchService;

    /**
     * 查询项目监控列表
     */
    @SaCheckPermission("w2:configSwitch:list")
    @GetMapping("/list")
    public TableDataInfo<W2ConfigSwitchVo> list(W2ConfigSwitchBo bo, PageQuery pageQuery) {
        return iW2ConfigSwitchService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出项目监控列表
     */
    @SaCheckPermission("w2:configSwitch:export")
    @Log(title = "项目监控", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2ConfigSwitchBo bo, HttpServletResponse response) {
        List<W2ConfigSwitchVo> list = iW2ConfigSwitchService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目监控", W2ConfigSwitchVo.class, response);
    }

    /**
     * 获取项目监控详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:configSwitch:query")
    @GetMapping("/{id}")
    public R<W2ConfigSwitchVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2ConfigSwitchService.queryById(id));
    }

    /**
     * 新增项目监控
     */
    @SaCheckPermission("w2:configSwitch:add")
    @Log(title = "项目监控", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2ConfigSwitchBo bo) {
        return toAjax(iW2ConfigSwitchService.insertByBo(bo));
    }

    /**
     * 修改项目监控
     */
    @SaCheckPermission("w2:configSwitch:edit")
    @Log(title = "项目监控", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2ConfigSwitchBo bo) {
        return toAjax(iW2ConfigSwitchService.updateByBo(bo));
    }

    /**
     * 删除项目监控
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:configSwitch:remove")
    @Log(title = "项目监控", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2ConfigSwitchService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    @SaCheckPermission("w2:configSwitch:import")
    @Log(title = "导入项目监控信息", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public R<Void> importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), W2ConfigSwitchVo.class, new PageReadListener<W2ConfigSwitchVo>(
                        iW2ConfigSwitchService::insertBatch
                ))
                .headRowNumber(1)
                .sheet().doRead();
        return R.ok("导入成功");
    }
}
