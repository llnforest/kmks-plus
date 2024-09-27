package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.kmks.jianguanold.domain.bo.A17C04Bo;
import com.kmks.jianguanold.domain.bo.A17C05Bo;
import com.kmks.jianguanold.domain.vo.A17C04Vo;
import com.kmks.jianguanold.domain.vo.A17C05Vo;
import com.kmks.jianguanold.service.IJgOldService;
import com.kmks.w2.service.WebService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.system.service.ISysConfigService;
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
import com.kmks.w2.domain.vo.W2SystemVo;
import com.kmks.w2.domain.bo.W2SystemBo;
import com.kmks.w2.service.IW2SystemService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基础编码
 *
 * @author ruoyi
 * @date 2023-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/school")
public class W2SystemController extends BaseController {

    private final IW2SystemService iW2SystemService;
    private final IJgOldService jgOldService;

    private final ISysConfigService configService;

    /**
     * 查询基础编码列表
     */
    @SaCheckPermission("w2:school:list")
    @GetMapping("/list")
    public TableDataInfo<W2SystemVo> list(W2SystemBo bo, PageQuery pageQuery) {
        return iW2SystemService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出基础编码列表
     */
    @SaCheckPermission("w2:school:export")
    @Log(title = "基础编码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2SystemBo bo, HttpServletResponse response) {
        List<W2SystemVo> list = iW2SystemService.queryList(bo);
        ExcelUtil.exportExcel(list, "基础编码", W2SystemVo.class, response);
    }

    /**
     * 获取基础编码详细信息
     *
     * @param nid 主键
     */
    @SaCheckPermission("w2:school:query")
    @GetMapping("/{nid}")
    public R<W2SystemVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long nid) {
        return R.ok(iW2SystemService.queryById(nid));
    }

    /**
     * 新增基础编码
     */
    @SaCheckPermission("w2:school:add")
    @Log(title = "基础编码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2SystemBo bo) {
        return toAjax(iW2SystemService.insertByBo(bo));
    }

    /**
     * 修改基础编码
     */
    @SaCheckPermission("w2:school:edit")
    @Log(title = "基础编码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2SystemBo bo) {
        return toAjax(iW2SystemService.updateByBo(bo));
    }

    /**
     * 删除基础编码
     *
     * @param nids 主键串
     */
    @SaCheckPermission("w2:school:remove")
    @Log(title = "基础编码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{nids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] nids) {
        return toAjax(iW2SystemService.deleteWithValidByIds(Arrays.asList(nids), true));
    }

    /**
     * 选择查找
     *
     * @param bo
     */
    @PostMapping("/select")
    public R<List<W2SystemVo>> select(@RequestBody W2SystemBo bo) {
        return R.ok(iW2SystemService.queryList(bo));
    }

    // ----------------------------------数据下载------------------------------------

    /**
     * 查询驾校信息列表
     */
    @SaCheckPermission("w2:school:dataList")
    @GetMapping("/dataList")
    public TableDataInfo<W2SystemVo> dataList(W2SystemBo bo, PageQuery pageQuery) {
        return iW2SystemService.queryPageList(bo, pageQuery);
    }

    /**
     * 下载驾校信息
     */
    @SaCheckPermission("w2:school:download")
    @Log(title = "驾校备案信息下载", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public void download(HttpServletResponse response) {
        A17C05Bo a17C05Bo = new A17C05Bo();
        a17C05Bo.setFzjg(configService.selectConfigByKey(CacheNames.JG_OLD_FZJG));
        A17C05Vo a17C05Vo = jgOldService.a17c05(a17C05Bo);
        ExcelUtil.exportExcel(a17C05Vo.getBody(), "驾校备案信息", A17C05Vo.Body.class, response);
    }

    /**
     * 导出驾校信息
     */
    @SaCheckPermission("w2:school:exportData")
    @Log(title = "驾校信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportData")
    public void exportData(W2SystemBo bo, HttpServletResponse response) {
        List<W2SystemVo> list = iW2SystemService.queryList(bo);
        ExcelUtil.exportExcel(list, "基础编码", W2SystemVo.class, response, "data");
    }

}
