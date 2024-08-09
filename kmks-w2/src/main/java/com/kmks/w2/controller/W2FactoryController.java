package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2FactoryBo;
import com.kmks.w2.domain.vo.W2FactoryVo;
import com.kmks.w2.service.WebService;
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
import com.kmks.w2.service.IW2FactoryService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考点信息
 *
 * @author lynn
 * @date 2023-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/factory")
public class W2FactoryController extends BaseController {

    private final IW2FactoryService iW2FactoryService;
    private final WebService webService;

    /**
     * 查询考点信息列表
     */
    @SaCheckPermission("w2:factory:list")
    @GetMapping("/list")
    public TableDataInfo<W2FactoryVo> list(W2FactoryBo bo, PageQuery pageQuery) {
        return iW2FactoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考点信息列表
     */
    @SaCheckPermission("w2:factory:export")
    @Log(title = "考点信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2FactoryBo bo, HttpServletResponse response) {
        List<W2FactoryVo> list = iW2FactoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "考点信息", W2FactoryVo.class, response);
    }

    /**
     * 获取考点信息详细信息
     *
     * @param xh 主键
     */
    @SaCheckPermission("w2:factory:query")
    @GetMapping("/{xh}")
    public R<W2FactoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String xh) {
        return R.ok(iW2FactoryService.queryById(xh));
    }

    /**
     * 新增考点信息
     */
    @SaCheckPermission("w2:factory:add")
    @Log(title = "考点信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2FactoryBo bo) {
        return toAjax(iW2FactoryService.insertByBo(bo));
    }

    /**
     * 修改考点信息
     */
    @SaCheckPermission("w2:factory:edit")
    @Log(title = "考点信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2FactoryBo bo) {
        return toAjax(iW2FactoryService.updateByBo(bo));
    }

    /**
     * 删除考点信息
     *
     * @param xhs 主键串
     */
    @SaCheckPermission("w2:factory:remove")
    @Log(title = "考点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xhs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] xhs) {
        return toAjax(iW2FactoryService.deleteWithValidByIds(Arrays.asList(xhs), true));
    }

    /**
     * 下载明细分组信息
     */
    @SaCheckPermission("w2:factory:download")
    @Log(title = "考点信息下载", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public R<Void> download() {
        String result = webService.Down17C01Xml();
        if(result.equals("")){
            return toAjax(true);
        }else{
            return toAjax(false);
        }
    }
}
