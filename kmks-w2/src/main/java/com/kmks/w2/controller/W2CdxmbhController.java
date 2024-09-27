package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.jianguanold.domain.bo.A17C02Bo;
import com.kmks.jianguanold.domain.bo.A17C05Bo;
import com.kmks.jianguanold.domain.vo.A17C02Vo;
import com.kmks.jianguanold.domain.vo.A17C05Vo;
import com.kmks.jianguanold.service.IJgOldService;
import com.kmks.jianguanold.service.impl.JgOldServiceImpl;
import com.kmks.w2.domain.EquipmentBean;
import com.kmks.w2.domain.bo.W2CdxmbhBo;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.service.IW2CdxmbhService;
import com.kmks.w2.service.WebService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.validate.CustomGroup;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 场地项目编号
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/cdxmbh")
public class W2CdxmbhController extends BaseController {

    private final IW2CdxmbhService iW2CdxmbhService;
    private final ISysConfigService configService;

    private final IJgOldService jgOldService;

    /**
     * 查询场地项目编号列表
     */
    @SaCheckPermission("w2:cdxmbh:list")
    @GetMapping("/list")
    public TableDataInfo<W2CdxmbhVo> list(W2CdxmbhBo bo, PageQuery pageQuery) {
        return iW2CdxmbhService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出场地项目编号列表
     */
    @SaCheckPermission("w2:cdxmbh:export")
    @Log(title = "场地项目编号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2CdxmbhBo bo, HttpServletResponse response) {
        List<W2CdxmbhVo> list = iW2CdxmbhService.queryList(bo);
        ExcelUtil.exportExcel(list, "场地项目编号", W2CdxmbhVo.class, response);
    }

    /**
     * 获取场地项目编号详细信息
     *
     * @param nid 主键
     */
    @SaCheckPermission("w2:cdxmbh:query")
    @GetMapping("/{nid}")
    public R<W2CdxmbhVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long nid) {
        return R.ok(iW2CdxmbhService.queryById(nid));
    }

    /**
     * 新增场地项目编号
     */
    @SaCheckPermission("w2:cdxmbh:add")
    @Log(title = "场地项目编号", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2CdxmbhBo bo) {
        return toAjax(iW2CdxmbhService.insertByBo(bo));
    }

    /**
     * 修改场地项目编号
     */
    @SaCheckPermission("w2:cdxmbh:edit")
    @Log(title = "场地项目编号", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2CdxmbhBo bo) {
        return toAjax(iW2CdxmbhService.updateByBo(bo));
    }

    /**
     * 删除场地项目编号
     *
     * @param nids 主键串
     */
    @SaCheckPermission("w2:cdxmbh:remove")
    @Log(title = "场地项目编号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{nids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] nids) {
        return toAjax(iW2CdxmbhService.deleteWithValidByIds(Arrays.asList(nids), true));
    }

    /**
     * 查询考场设备编号
     **/
    @PostMapping("/select")
    public R<List<W2CdxmbhVo>> select(@RequestBody W2CdxmbhBo bo) {
        return R.ok(iW2CdxmbhService.queryList(bo));
    }

    /**
     * 设备项统计
     */
    @PostMapping("/listCdxmbhByLogNew")
    public TableDataInfo<EquipmentBean> listCdxmbhByLogNew(@RequestBody PageQuery pageQuery) {
        Page<EquipmentBean> page = pageQuery.build();
        return TableDataInfo.build(iW2CdxmbhService.listCdxmbhByLogNew(page));
    }

    /**
     * 下载设备备案信息
     */
    @SaCheckPermission("w2:cdxmbh:download")
    @Log(title = "设备备案信息下载", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public void download(HttpServletResponse response) {
        A17C02Bo a17C02Bo = new A17C02Bo();
        a17C02Bo.setFzjg(configService.selectConfigByKey(CacheNames.JG_OLD_FZJG));
        a17C02Bo.setKcxh(configService.selectConfigByKey(CacheNames.JG_COMMON_KSXTXH));
        A17C02Vo a17C02Vo = jgOldService.a17c02(a17C02Bo);
        ExcelUtil.exportExcel(a17C02Vo.getBody(), "设备备案信息", A17C02Vo.Body.class, response);
    }
    
    // ----------------------------------数据下载------------------------------------

    /**
     * 查询场地项目编号列表
     */
    @SaCheckPermission("w2:cdxmbh:dataList")
    @GetMapping("/dataList")
    public TableDataInfo<W2CdxmbhVo> dataList(W2CdxmbhBo bo, PageQuery pageQuery) {
        return iW2CdxmbhService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出场地项目编号
     */
    @SaCheckPermission("w2:cdxmbh:exportData")
    @Log(title = "场地项目编号", businessType = BusinessType.EXPORT)
    @PostMapping("/exportData")
    public void exportData(W2CdxmbhBo bo, HttpServletResponse response) {
        List<W2CdxmbhVo> list = iW2CdxmbhService.queryList(bo);
        ExcelUtil.exportExcel(list, "场地项目编号", W2CdxmbhVo.class, response, "data");
    }

    /**
     * 更新场地项目编号
     */
    @SaCheckPermission("w2:cdxmbh:editData")
    @Log(title = "场地项目编号", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/updateData")
    public R<Void> updateData(@Validated(CustomGroup.class) @RequestBody W2CdxmbhBo bo) {
        return toAjax(iW2CdxmbhService.updateByBo(bo));
    }


}
