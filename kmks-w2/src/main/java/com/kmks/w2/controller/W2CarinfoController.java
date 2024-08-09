package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

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
import com.kmks.w2.domain.vo.W2CarinfoVo;
import com.kmks.w2.domain.bo.W2CarinfoBo;
import com.kmks.w2.service.IW2CarinfoService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考车信息
 *
 * @author lynn
 * @date 2023-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/carinfo")
public class W2CarinfoController extends BaseController {

    private final IW2CarinfoService iW2CarinfoService;
    private final WebService webService;

    /**
     * 查询考车信息列表
     */
    @SaCheckPermission("w2:carinfo:list")
    @GetMapping("/list")
    public TableDataInfo<W2CarinfoVo> list(W2CarinfoBo bo, PageQuery pageQuery) {
        return iW2CarinfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考车信息列表
     */
    @SaCheckPermission("w2:carinfo:export")
    @Log(title = "考车信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2CarinfoBo bo, HttpServletResponse response) {
        List<W2CarinfoVo> list = iW2CarinfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "考车信息", W2CarinfoVo.class, response);
    }

    /**
     * 获取考车信息详细信息
     *
     * @param xh 主键
     */
    @SaCheckPermission("w2:carinfo:query")
    @GetMapping("/{xh}")
    public R<W2CarinfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String xh) {
        return R.ok(iW2CarinfoService.queryById(xh));
    }

    /**
     * 新增考车信息
     */
    @SaCheckPermission("w2:carinfo:add")
    @Log(title = "考车信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2CarinfoBo bo) {
        return toAjax(iW2CarinfoService.insertByBo(bo));
    }

    /**
     * 修改考车信息
     */
    @SaCheckPermission("w2:carinfo:edit")
    @Log(title = "考车信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2CarinfoBo bo) {
        return toAjax(iW2CarinfoService.updateByBo(bo));
    }

    /**
     * 删除考车信息
     *
     * @param xhs 主键串
     */
    @SaCheckPermission("w2:carinfo:remove")
    @Log(title = "考车信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xhs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] xhs) {
        return toAjax(iW2CarinfoService.deleteWithValidByIds(Arrays.asList(xhs), true));
    }

    /**
     * 下载考车信息
     */
    @SaCheckPermission("w2:carinfo:download")
    @Log(title = "车辆下载", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public R<Void> download() {
        String result = webService.Down17C03Xml();
        if(result.equals("")){
            return toAjax(true);
        }else{
            return toAjax(false);
        }
    }
}
