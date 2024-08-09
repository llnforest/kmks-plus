package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.w2.domain.bo.W2GroupsTempBo;
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
import com.kmks.w2.domain.vo.W2GroupsTempVo;
import com.kmks.w2.service.IW2GroupsTempService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 查询下载信息
 *
 * @author lynn
 * @date 2023-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/groupsTemp")
public class W2GroupsTempController extends BaseController {

    private final IW2GroupsTempService iW2GroupsTempService;
    private final WebService webService;

    /**
     * 查询查询下载信息列表
     */
    @SaCheckPermission("w2:groupsTemp:list")
    @GetMapping("/list")
    public TableDataInfo<W2GroupsTempVo> list(W2GroupsTempBo bo, PageQuery pageQuery) {
        return iW2GroupsTempService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出查询下载信息列表
     */
    @SaCheckPermission("w2:groupsTemp:export")
    @Log(title = "查询下载信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2GroupsTempBo bo, HttpServletResponse response) {
        List<W2GroupsTempVo> list = iW2GroupsTempService.queryList(bo);
        ExcelUtil.exportExcel(list, "查询下载信息", W2GroupsTempVo.class, response);
    }

    /**
     * 获取查询下载信息详细信息
     *
     * @param lsh 主键
     */
    @SaCheckPermission("w2:groupsTemp:query")
    @GetMapping("/{lsh}")
    public R<W2GroupsTempVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String lsh) {
        return R.ok(iW2GroupsTempService.queryById(lsh));
    }

    /**
     * 新增查询下载信息
     */
    @SaCheckPermission("w2:groupsTemp:add")
    @Log(title = "查询下载信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2GroupsTempBo bo) {
        return toAjax(iW2GroupsTempService.insertByBo(bo));
    }

    /**
     * 修改查询下载信息
     */
    @SaCheckPermission("w2:groupsTemp:edit")
    @Log(title = "查询下载信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2GroupsTempBo bo) {
        return toAjax(iW2GroupsTempService.updateByBo(bo));
    }

    /**
     * 删除查询下载信息
     *
     * @param lshs 主键串
     */
    @SaCheckPermission("w2:groupsTemp:remove")
    @Log(title = "查询下载信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lshs}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] lshs) {
        return toAjax(iW2GroupsTempService.deleteWithValidByIds(Arrays.asList(lshs), true));
    }

    /**
     * 下载查询下载信息
     */
    @SaCheckPermission("w2:groupsTemp:download")
    @Log(title = "查询下载信息", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public R<Void> download() {
        String result = webService.Down17C08Xml(DateUtils.getDate(), "0");
        if(result.equals("")){
            return toAjax(true);
        }else{
            return toAjax(false);
        }
    }
}
