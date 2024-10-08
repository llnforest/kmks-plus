package com.kmks.w2.controller;

import java.io.File;
import java.util.*;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kmks.w2.config.ConfigProperties;
import com.kmks.w2.domain.RemoteData;
import com.kmks.w2.domain.W2Kfconfig;
import com.kmks.w2.domain.bo.W2GpscontentBo;
import com.kmks.w2.domain.dto.RecordsResetDto;
import com.kmks.w2.domain.vo.PassRateVo;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.vo.W2GpscontentVo;
import com.kmks.w2.service.*;
import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.barcode.BarcodeUtil;
import com.ruoyi.common.config.WebServiceConfig;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.service.DictService;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.report.JasperReportUtil;
import com.ruoyi.common.webservice.WebServiceUtil;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 成绩管理
 *
 * @author lynn
 * @date 2023-04-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/records")
public class W2RecordsController extends BaseController {

    private final IW2RecordsService iW2RecordsService;

    private final IW2GpscontentService iw2GpscontentService;

    private final DictService dictService;

    private final IW2KfconfigService iw2KfconfigService;

    private final WebService webService;
    @Autowired
    private ConfigProperties configProperties;

    private final WebServiceConfig webServiceConfig;

    private final ISysConfigService configService;

    /**
     * 查询成绩管理列表
     */
    @SaCheckPermission("w2:records:list")
    @Log(title = "成绩管理", businessType = BusinessType.QUERY, remark = "查询列表")
    @GetMapping("/list")
    public TableDataInfo<W2RecordsVo> list(W2RecordsBo bo, PageQuery pageQuery) {
        return iW2RecordsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出成绩管理列表
     */
    @SaCheckPermission("w2:records:export")
    @Log(title = "成绩管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2RecordsBo bo, HttpServletResponse response) {
        List<W2RecordsVo> list = iW2RecordsService.queryList(bo);
        ExcelUtil.exportExcel(list, "成绩管理", W2RecordsVo.class, response);
    }

    /**
     * 导出Gps轨迹
     */
    @SaCheckPermission("w2:records:exportGps")
    @Log(title = "成绩管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGps")
    public void exportGps(@NotNull(message = "id不能为空") @RequestParam("id") Long id, HttpServletResponse response) {
        W2RecordsVo w2RecordsVo = iW2RecordsService.queryById(id);

        W2GpscontentBo w2GpscontentBo = new W2GpscontentBo();
        w2GpscontentBo.setCarno(w2RecordsVo.getKcbh());
        w2GpscontentBo.setSfzhm(w2RecordsVo.getZjhm());
        w2RecordsVo.getKsrq1();
        w2GpscontentBo.setTableMark(DateUtil.format(w2RecordsVo.getKsrq1(), "yyyyMM"));
        w2GpscontentBo.setSendrqStart(Long.valueOf(DateUtil.format(w2RecordsVo.getKsrq1(), "yyyyMMddHHmmss")));
        w2GpscontentBo.setSendrqEnd(Long.valueOf(DateUtil.format(w2RecordsVo.getJssj2(), "yyyyMMddHHmmss")));

        List<W2GpscontentVo> list = iw2GpscontentService.queryListByTable(w2GpscontentBo, W2GpscontentVo.class);
        ExcelUtil.exportExcel(list, "轨迹管理", W2GpscontentVo.class, response);
    }

    /**
     * 获取成绩管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:records:query")
    @Log(title = "成绩管理", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public R<W2RecordsVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long id) {
        return R.ok(iW2RecordsService.queryById(id));
    }

    /**
     * 新增成绩管理
     */
    @SaCheckPermission("w2:records:add")
    @Log(title = "成绩管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2RecordsBo bo) {
        return toAjax(iW2RecordsService.insertByBo(bo));
    }

    /**
     * 修改成绩管理
     */
    @SaCheckPermission("w2:records:edit")
    @Log(title = "成绩管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2RecordsBo bo) {
        return toAjax(iW2RecordsService.updateByBo(bo));
    }

    /**
     * 删除成绩管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:records:remove")
    @Log(title = "成绩管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2RecordsService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 误判重考
     */
    @SaCheckPermission("w2:records:resetRecord")
    @Log(title = "成绩管理", businessType = BusinessType.UPDATE, remark = "误判重考")
    @RepeatSubmit()
    @PutMapping("/resetRecord")
    public R<Void> resetRecord(@Validated(EditGroup.class) @RequestBody RecordsResetDto dto) {
        // 不走webservice
        if (!webServiceConfig.enabled) {
            return toAjax(iW2RecordsService.resetRecord(dto));
        } else {
            W2RecordsVo w2RecordsVo = iW2RecordsService.queryById(dto.getId());
            // 走webservice
            StringBuilder msg = new StringBuilder();
            msg.append(String.format("%s;%s;%s;%s;%s", dto.getWpyy(), w2RecordsVo.getKsbh(), LoginHelper.getLoginUser().getUsername(), DateUtils.getDate(), dto.getWpxz()));
            // 夜考模式
            if (configProperties.getSfyk() != -1) {
                msg.append(String.format(";%s", w2RecordsVo.getSfyk()));
            }
            // 申请误判重考接口
            String result = webService.ReExamine(msg.toString());
            if (StringUtils.contains(result, "成功")) {
                return toAjax(true);
            } else {
                return toAjax(false);
            }
        }


    }

    /**
     * 打印报告
     *
     * @param id id
     * @throws Exception 异常
     */
    @SaCheckPermission("w2:records:print")
    @Log(title = "成绩管理", businessType = BusinessType.GRANT, remark = "打印成绩单")
    @GetMapping("/getReport/{id}")
    public void getReport(@PathVariable("id") Long id) throws Exception {
        W2RecordsVo w2RecordsVo = iW2RecordsService.queryById(id);
        W2RecordsBo w2RecordsBo = new W2RecordsBo();
        w2RecordsBo.setId(id);
        w2RecordsBo.setDycs(1L);
        w2RecordsBo.setSfprint("Y");
        iW2RecordsService.updateByBo(w2RecordsBo);


        // 创建jaster report 中所需的parameter
        HashMap<String, String> parameterMap = new HashMap<>();
//        parameterMap.put("title",dictService.getDictLabel("sys_kskm",w2RecordsVo.getKskm())+"考试成绩单");//字典record_kskm
        parameterMap.put("xm", w2RecordsVo.getXm());
        parameterMap.put("xxjszm", w2RecordsVo.getZkzh());
        parameterMap.put("sfzmhm", w2RecordsVo.getZjhm());
        parameterMap.put("bkcx", w2RecordsVo.getKscx());
        parameterMap.put("ywlx", dictService.getDictLabel("record_ksyy", w2RecordsVo.getKsyy()));//字典record_ksyy
        parameterMap.put("ksrq", DateUtils.parseDateToStr("yyyy-MM-dd", w2RecordsVo.getKsrq()));
        parameterMap.put("yycs", w2RecordsVo.getYycs().toString());
//        parameterMap.put("ksdd","合肥市蜀山区八一驾校");

        parameterMap.put("ksy", w2RecordsVo.getKsy1());
        // 第一次考试参数设置
//        parameterMap.put("kskm",w2RecordsVo.getKskm().equals("3")?"科目三道路驾驶技能考试":"科目二场地驾驶技能考试");
        parameterMap.put("kssj", DateUtils.parseDateToStr("HH:mm:ss", w2RecordsVo.getKssj1()) + "~" + DateUtils.parseDateToStr("HH:mm:ss", w2RecordsVo.getJssj1()));
        parameterMap.put("kscj", dictService.getDictLabel("record_ksjg", w2RecordsVo.getKscj1()) + String.format("（%s）", w2RecordsVo.getJgfs1()));


        //扣分项
        handleKf(parameterMap, w2RecordsVo.getKfxx1(), "kfx");
        parameterMap.put("title", dictService.getDictLabel("sys_kskm", w2RecordsVo.getKskm()) + configService.selectConfigByKey(CacheNames.PRINT_SCORE_TITLE));
        parameterMap.put("ksdd", configService.selectConfigByKey(CacheNames.SCHOOL_NAME));
        parameterMap.put("kskm", dictService.getDictLabel("sys_kskm", w2RecordsVo.getKskm()) + "考试");
        parameterMap.put("bkkskm", dictService.getDictLabel("sys_kskm", w2RecordsVo.getKskm()) + "补考");


        // 补考参数设置
        if (!w2RecordsVo.getKscj1().equals("1") && !w2RecordsVo.getKscj2().equals("0")) {
//            parameterMap.put("bkkskm",w2RecordsVo.getKskm().equals("3")?"科目三道路驾驶技能补考":"科目二场地驾驶技能补考");
            parameterMap.put("bkkssj", DateUtils.parseDateToStr("HH:mm:ss", w2RecordsVo.getKssj2()) + "~" + DateUtils.parseDateToStr("HH:mm:ss", w2RecordsVo.getJssj2()));
            parameterMap.put("bkkscj", dictService.getDictLabel("record_ksjg", w2RecordsVo.getKscj2()) + String.format("（%s）", w2RecordsVo.getJgfs2()));
            // 第二次考试三张照片
            parameterMap.put("bkImg6", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq2(), "6")));
            parameterMap.put("bkImg7", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq2(), "7")));
            parameterMap.put("bkImg8", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq2(), "8")));
        } else {
            parameterMap.put("bkkskm", "");
            parameterMap.put("bkkssj", "");
            parameterMap.put("bkkscj", "");
            parameterMap.put("bkkfx1", "");
            parameterMap.put("bkkfx2", "");
            parameterMap.put("bkkfx3", "");
            parameterMap.put("bkkfx4", "");
        }

        handleKf(parameterMap, w2RecordsVo.getKfxx2(), "bkkfx");
        parameterMap.put("kgxm", w2RecordsVo.getKsy1());
        String barcodeEncode = Base64.encode(BarcodeUtil.generate(w2RecordsVo.getZjhm()));
        parameterMap.put("bcodeImg", barcodeEncode);
        //jbzp
//        String headEncode = Base64.encode(new File("C:\\Users\\Star\\Desktop\\image\\2.jpg"));
        // 门禁照片
        parameterMap.put("headImg", FileUtil.convertImageToBase64(w2RecordsVo.getJbzp()));
        // 第一次考试三张照片
//        FileUtil
        parameterMap.put("ksImg1", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq1(), "3")));
        parameterMap.put("ksImg2", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq1(), "4")));
        parameterMap.put("ksImg3", webService.downUpInfo(String.format("$CZ;3002;%s;%s;%s", w2RecordsVo.getZjhm(), w2RecordsVo.getKsrq1(), "5")));
//        webService.downUpInfo("$CZ;3002;211022198511034902;2019-09-16 00:00:00;4");
        String jasperPath = JasperReportUtil.getJasperFileDir(configService.selectConfigByKey(CacheNames.PRINT_SCORE_TEMPLATE));
        JasperReportUtil.exportToPdf(jasperPath, parameterMap, null, ServletUtils.getResponse());

    }

    /**
     * 处理扣分项
     */
    private void handleKf(Map<String, String> parameterMap, String kfxx, String kfx) {
        List<W2Kfconfig> w2Kfconfigs = null;
        if (ObjectUtil.isNotNull(kfxx)) {
            w2Kfconfigs = iw2KfconfigService.queryInIds(kfxx);
        }
        for (int i = 1; i <= 4; i++) {
            if (w2Kfconfigs == null || w2Kfconfigs.size() < i) {
                parameterMap.put(kfx + i, "");
            } else {
                parameterMap.put(kfx + i, String.format("%s：%s（-%s）:%s", w2Kfconfigs.get(i - 1).getGakfmc(), w2Kfconfigs.get(i - 1).getId(), w2Kfconfigs.get(i - 1).getKf(), w2Kfconfigs.get(i - 1).getKfmc()));
            }
        }
    }

    /**
     * 合格率统计
     *
     * @param bo bo
     * @return {@link R}<{@link List}<{@link PassRateVo}>>
     */
    @SaCheckPermission("w2:records:getAnalyseHgl")
    @Log(title = "考试数据管理", businessType = BusinessType.QUERY, remark = "查询合格率统计")
    @GetMapping("/getAnalyseHgl")
    public R<List<PassRateVo>> getAnalyseHgl(W2RecordsBo bo) {
        List<PassRateVo> passRateVoList = iW2RecordsService.queryRateList(bo);
        return R.ok(passRateVoList);
    }

    /**
     * 获取异地登录统计
     */
    @GetMapping("/remote")
    public R<List<RemoteData>> selectSfzByZjhm() {
        List<RemoteData> remoteDatas = new ArrayList<>();
        int count = iW2RecordsService.selectSfzByZjhm().size();
        // iW2RecordsService.selectSfzByZjhm().stream().filter(p -> p >= 0)
        RemoteData remoteData0 = new RemoteData();
        remoteData0.setField("本地");
        remoteData0.setNum(count);
        remoteData0.setPercentage(NumberUtil.mul(100, NumberUtil.div(count, count)));
        remoteDatas.add(remoteData0);
        RemoteData remoteData1 = new RemoteData();
        remoteData1.setField("异地本省");
        remoteData1.setNum(count);
        remoteData1.setPercentage(NumberUtil.mul(100, NumberUtil.div(count, count)));
        remoteDatas.add(remoteData1);
        RemoteData remoteData2 = new RemoteData();
        remoteData2.setField("异地外省");
        remoteData2.setNum(count);
        remoteData2.setPercentage(NumberUtil.mul(100, NumberUtil.div(count, count)));
        remoteDatas.add(remoteData2);
        return R.ok(remoteDatas);
    }

    /**
     * 考试过程信息
     */
    @SaCheckPermission("w2:records:detail")
    @Log(title = "成绩管理", businessType = BusinessType.QUERY, remark = "查询考试过程")
    @PostMapping("/flowList")
    public R<List<W2FlowVo>> flowList(String zjhm, String ksrq) {
        return R.ok(iW2RecordsService.getFlowList(zjhm, ksrq));
    }

}
