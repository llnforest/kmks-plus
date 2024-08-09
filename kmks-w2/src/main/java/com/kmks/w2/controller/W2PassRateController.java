package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.domain.vo.PassRateDetailVo;
import com.kmks.w2.domain.vo.PassRateTotalVo;
import com.kmks.w2.service.IW2RecordsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 成绩管理
 *
 * @author lynn
 * @date 2023-04-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/passRate")
public class W2PassRateController extends BaseController {

    private final IW2RecordsService iW2RecordsService;


    /**
     * 查询合格率统计 总
     */
    @SaCheckPermission("w2:passRate:totalList")
    @GetMapping("/totalList")
    public TableDataInfo<PassRateTotalVo> totalList(W2RecordsBo bo) {
        List<PassRateTotalVo> w2RecordsVos = iW2RecordsService.queryRateTotalList(bo);

        return TableDataInfo.build(w2RecordsVos);
    }

    /**
     * 查询合格率统计 明细
     */
    @SaCheckPermission("w2:passRate:detailList")
    @GetMapping("/detailList")
    public TableDataInfo<PassRateDetailVo> detailList(W2RecordsBo bo) {

        return TableDataInfo.build(iW2RecordsService.queryRateDetailList(bo));
    }

    /**
     * 导出总合格率
     */
    @SaCheckPermission("w2:passRate:exportTotal")
    @Log(title = "总合格率", businessType = BusinessType.EXPORT)
    @PostMapping("/exportTotal")
    public void exportTotal(W2RecordsBo bo, HttpServletResponse response) {
        List<PassRateTotalVo> list = iW2RecordsService.queryRateTotalList(bo);
        ExcelUtil.exportExcel(list, "总合格率", PassRateTotalVo.class, response);
    }

    /**
     * 导出明细合格率
     */
    @SaCheckPermission("w2:passRate:exportDetail")
    @Log(title = "明细合格率", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDetail")
    public void exportDetail(W2RecordsBo bo, HttpServletResponse response) {
        List<PassRateDetailVo> list = iW2RecordsService.queryRateDetailList(bo);
        ExcelUtil.exportExcel(list, "明细合格率", PassRateDetailVo.class, response);
    }

}
