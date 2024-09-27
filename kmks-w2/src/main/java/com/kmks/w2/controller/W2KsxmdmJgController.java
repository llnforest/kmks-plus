package com.kmks.w2.controller;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.jianguanold.domain.bo.A17C01Bo;
import com.kmks.jianguanold.domain.bo.A17C02Bo;
import com.kmks.jianguanold.domain.vo.A17C01Vo;
import com.kmks.jianguanold.domain.vo.A17C02Vo;
import com.kmks.jianguanold.service.IJgOldService;
import com.kmks.w2.domain.KfCodeReport;
import com.kmks.w2.service.IW2RecordsService;
import com.kmks.w2.domain.vo.W2KsxmdmJgVo;
import com.kmks.w2.service.IW2KsxmdmJgService;
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
import com.kmks.w2.domain.bo.W2KsxmdmJgBo;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目代码
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/ksxmdmJg")
public class W2KsxmdmJgController extends BaseController {

    private final IW2KsxmdmJgService iW2KsxmdmJgService;

    private final IW2RecordsService iW2RecordsService;

    private final ISysConfigService configService;

    private final IJgOldService jgOldService;

    /**
     * 查询项目代码列表
     */
    @SaCheckPermission("w2:ksxmdmJg:list")
    @GetMapping("/list")
    public TableDataInfo<W2KsxmdmJgVo> list(W2KsxmdmJgBo bo, PageQuery pageQuery) {
        return iW2KsxmdmJgService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出项目代码列表
     */
    @SaCheckPermission("w2:ksxmdmJg:export")
    @Log(title = "项目代码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KsxmdmJgBo bo, HttpServletResponse response) {
        List<W2KsxmdmJgVo> list = iW2KsxmdmJgService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目代码", W2KsxmdmJgVo.class, response);
    }

    /**
     * 获取项目代码详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:ksxmdmJg:query")
    @GetMapping("/{id}")
    public R<W2KsxmdmJgVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable String id) {
        return R.ok(iW2KsxmdmJgService.queryById(id));
    }

    /**
     * 新增项目代码
     */
    @SaCheckPermission("w2:ksxmdmJg:add")
    @Log(title = "项目代码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KsxmdmJgBo bo) {
        return toAjax(iW2KsxmdmJgService.insertByBo(bo));
    }

    /**
     * 修改项目代码
     */
    @SaCheckPermission("w2:ksxmdmJg:edit")
    @Log(title = "项目代码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KsxmdmJgBo bo) {
        return toAjax(iW2KsxmdmJgService.updateByBo(bo));
    }

    /**
     * 删除项目代码
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:ksxmdmJg:remove")
    @Log(title = "项目代码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(iW2KsxmdmJgService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 选择查询
     *
     * @param bo
     */
    @PostMapping("/select")
    public R<List<W2KsxmdmJgVo>> select(@RequestBody W2KsxmdmJgBo bo) {
        return R.ok(iW2KsxmdmJgService.queryList(bo));
    }

    /**
     * 选择查询
     *
     * @param bo
     */
    @PostMapping("/selectMap")
    public R<Map<String, String>> selectMap(@RequestBody W2KsxmdmJgBo bo) {
        List<W2KsxmdmJgVo> w2KsxmdmJgVos = iW2KsxmdmJgService.queryList(bo);
//      Map<String, String> collect = w2KsxmdmJgVos.stream().collect(Collectors.toMap(W2KsxmdmJgVo::getCustxh, vo -> vo.getName()));
        HashMap<String, String> map = new HashMap<>();
        CollectionUtil.toMap(w2KsxmdmJgVos, map, vo -> vo.getCustxh(), vo -> vo.getName());
        return R.ok(map);
    }

    /**
     * 误判审批统计使用
     *
     * @return
     */
    @PostMapping("/getAllKsxmByStatistics")
    public TableDataInfo<W2KsxmdmJgVo> getAllKsxmByStatistics(@RequestBody PageQuery pageQuery) {
        return iW2KsxmdmJgService.getAllKsxmByStatistics(pageQuery);
    }

    /**
     * 统计数据
     */
    @PostMapping("/getTotalKfCodeReport")
    public TableDataInfo<KfCodeReport> getTotalKfCodeReport(@RequestBody PageQuery pageQuery) {
        Page<KfCodeReport> page = pageQuery.build();
        Page<KfCodeReport> kfCodeReports = iW2KsxmdmJgService.getTotalKfCodeReport(page, "3");
        List<KfCodeReport> kfCodeReports1 = kfCodeReports.getRecords().stream().map(item -> {
            item.setXMMC(iW2KsxmdmJgService.getXMMCName("3", item.getKSXM()));
            item.setNUM1(iW2RecordsService.selectCountByGakfdm1(item.getKSXM()));
            item.setNUM2(iW2RecordsService.selectCountByGakfdm2(item.getKSXM()));
            item.setNUM(item.getNUM1() + item.getNUM2());
            item.setNUMS1(iW2RecordsService.selectCountByKfxx1());
            item.setNUMS2(iW2RecordsService.selectCountByKfxx2());
            item.setNUMS(item.getNUMS1() + item.getNUM2());
            if (item.getNUMS1() > 0) {
                item.setBL1(NumberUtil.div(item.getNUM1(), item.getNUMS1()).doubleValue());
            }
            if (item.getNUMS2() > 0) {
                item.setBL2(NumberUtil.div(item.getNUM2(), item.getNUMS2()).doubleValue());
            }
            if (item.getNUMS() > 0) {
                item.setBL(NumberUtil.div(item.getNUM(), item.getNUMS()).doubleValue());
                item.setTotalRatio(NumberUtil.div((double) item.getNUM(), NumberUtil.mul(item.getNUMS().toString(), String.valueOf(kfCodeReports.getSize())).doubleValue()));
            }
            return item;
        }).collect(Collectors.toList());
        kfCodeReports.setRecords(kfCodeReports1);
        return TableDataInfo.build(kfCodeReports);
    }

    /**
     * 统计数据
     */
    @PostMapping("/listKsxmdmJgByLogNew")
    public TableDataInfo<W2KsxmdmJgVo> listKsxmdmJgByLogNew(@RequestBody PageQuery pageQuery) {
        Page<W2KsxmdmJgVo> page = pageQuery.build();
        Page<W2KsxmdmJgVo> kfCodeReports = iW2KsxmdmJgService.listKsxmdmJgByLogNew(page);
        return TableDataInfo.build(kfCodeReports);
    }

    /**
     * 下载场地备案信息
     */
    @SaCheckPermission("w2:ksxmdmJg:download")
    @Log(title = "场地备案信息下载", businessType = BusinessType.UPDATE)
    @PostMapping("/download")
    public void download(HttpServletResponse response) {
        A17C01Bo a17C01Bo = new A17C01Bo();
        a17C01Bo.setFzjg(configService.selectConfigByKey(CacheNames.JG_OLD_FZJG));
        A17C01Vo a17C01Vo = jgOldService.a17c01(a17C01Bo);
        ExcelUtil.exportExcel(a17C01Vo.getBody(), "场地备案信息", A17C01Vo.Body.class, response);
    }

}
