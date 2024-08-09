package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2GroupsBo;
import com.kmks.w2.domain.vo.W2GroupsVo;
import com.kmks.w2.service.WebService;
import com.ruoyi.common.utils.DateUtils;
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
import com.kmks.w2.service.IW2GroupsService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 明细分组信息
 *
 * @author lynn
 * @date 2023-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/groups")
public class W2GroupsController extends BaseController {

    private final IW2GroupsService iW2GroupsService;

    private final WebService webService;

    /**
     * 查询明细分组信息列表
     */
    @SaCheckPermission("w2:groups:list")
    @GetMapping("/list")
    public TableDataInfo<W2GroupsVo> list(W2GroupsBo bo, PageQuery pageQuery) {
        return iW2GroupsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出明细分组信息列表
     */
    @SaCheckPermission("w2:groups:export")
    @Log(title = "明细分组信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2GroupsBo bo, HttpServletResponse response) {
        List<W2GroupsVo> list = iW2GroupsService.queryList(bo);
        ExcelUtil.exportExcel(list, "明细分组信息", W2GroupsVo.class, response);
    }

    /**
     * 获取明细分组信息详细信息
     *
     * @param xh 主键
     */
    @SaCheckPermission("w2:groups:query")
    @GetMapping("/{xh}")
    public R<W2GroupsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String xh) {
        return R.ok(iW2GroupsService.queryById(xh));
    }

    /**
     * 新增明细分组信息
     */
    @SaCheckPermission("w2:groups:add")
    @Log(title = "明细分组信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2GroupsBo bo) {
        return toAjax(iW2GroupsService.insertByBo(bo));
    }

    /**
     * 修改明细分组信息
     */
    @SaCheckPermission("w2:groups:edit")
    @Log(title = "明细分组信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2GroupsBo bo) {
        return toAjax(iW2GroupsService.updateByBo(bo));
    }

    /**
     * 删除明细分组信息
     *
     * @param xhs 主键串
     */
    @SaCheckPermission("w2:groups:remove")
    @Log(title = "明细分组信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xhs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] xhs) {
        return toAjax(iW2GroupsService.deleteWithValidByIds(Arrays.asList(xhs), true));
    }

    /**
     * 下载明细分组信息
     */
    @SaCheckPermission("w2:groups:download")
    @Log(title = "明细分组信息", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public R<Void> download() {
        String result = webService.Down17C06Xml(DateUtils.getDate());
        if(result.equals("")){
            return toAjax(true);
        }else{
            return toAjax(false);
        }
    }
}
