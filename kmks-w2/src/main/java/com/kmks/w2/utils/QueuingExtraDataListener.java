package com.kmks.w2.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.bo.W2CarModelBo;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.service.IW2CarModelService;
import com.kmks.w2.service.IW2QueuingService;
import com.ruoyi.common.enums.FileNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class QueuingExtraDataListener implements ReadListener<ExtraData> {

    private IW2QueuingService queuingService;

    List<ExtraData> cachedDataList = new ArrayList<>();
    List<W2QueuingBo> dataList = new ArrayList<>();
    List<W2RecordsBo> recordsDataList = new ArrayList<>();
    Map<String, String> dataMap = new HashMap<>();

    public QueuingExtraDataListener(IW2QueuingService iw2QueuingService, Map<String, String> dataMap, MultipartFile file, String filePath) {
        this.queuingService = iw2QueuingService;
        this.dataMap = dataMap;
        FileUtil.MultipartFileConvertToFile(filePath + FileNames.queuing + UUID.randomUUID() + ".xlsx", file);
    }

    @Override
    public void invoke(ExtraData data, AnalysisContext context) {
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        dealCachedData();
        saveData();
    }

    private void dealCachedData() {
        dataList = new ArrayList<>();
        if (cachedDataList == null) {
            return;
        }

        for (int i = 2; i < cachedDataList.size(); i++) {
            ExtraData extraData = cachedDataList.get(i);
            W2QueuingBo w2QueuingBo = new W2QueuingBo();
            // 排队列表
            w2QueuingBo.setXm(extraData.getRow3());
            w2QueuingBo.setZjhm(extraData.getRow4());
            w2QueuingBo.setKscx(extraData.getRow5());
            w2QueuingBo.setZkzh(extraData.getRow6());
            w2QueuingBo.setLsh(extraData.getRow7());
            w2QueuingBo.setKsyy(extraData.getRow8());
            w2QueuingBo.setKsrq(DateUtil.parse(extraData.getRow9()));
            w2QueuingBo.setKg(extraData.getRow10());
            w2QueuingBo.setKgname(extraData.getRow10());
            w2QueuingBo.setKg2(extraData.getRow11());
            w2QueuingBo.setYycs(extraData.getRow12());
            w2QueuingBo.setJxdm(extraData.getRow13());
            w2QueuingBo.setJxmc(extraData.getRow14());
            w2QueuingBo.setKscc(Long.valueOf(extraData.getRow15()));
            w2QueuingBo.setJbr(extraData.getRow16());
            w2QueuingBo.setGlbm(extraData.getRow17());
            w2QueuingBo.setBdxh(0l);
            w2QueuingBo.setKscs(1l);
            w2QueuingBo.setDjc(0l);
            w2QueuingBo.setSqks("1");
            w2QueuingBo.setScore(0l);
            w2QueuingBo.setBcyykscs("1");


            //成绩列表
            W2RecordsBo w2RecordsBo = new W2RecordsBo();
            w2RecordsBo.setXm(extraData.getRow3());
            w2RecordsBo.setZjhm(extraData.getRow4());
            w2RecordsBo.setKscx(extraData.getRow5());
            w2RecordsBo.setZkzh(extraData.getRow6());
            w2RecordsBo.setLsh(extraData.getRow7());
            w2RecordsBo.setKsyy(extraData.getRow8());
            w2RecordsBo.setKsrq(DateUtil.parse(extraData.getRow9()));
            w2RecordsBo.setKsy1(extraData.getRow10());
            w2RecordsBo.setKsy2(extraData.getRow11());
            w2RecordsBo.setYycs(Long.valueOf(extraData.getRow12()));
            w2RecordsBo.setJxdm(extraData.getRow13());
            w2RecordsBo.setJxmc(extraData.getRow14());
            w2RecordsBo.setKscc(extraData.getRow15());
            w2RecordsBo.setJbr(extraData.getRow16());
            w2RecordsBo.setGlbm(extraData.getRow17());
            w2RecordsBo.setKscj1("0");
            w2RecordsBo.setKscj2("0");
            w2RecordsBo.setKscs(1l);
            w2RecordsBo.setBcyykscs("1");
            w2RecordsBo.setKszt("0");

            // 根据科目二科目三分别插入数据
            if(extraData.getRow1().equals("1")){
                w2QueuingBo.setKskm("2");
                dataList.add(w2QueuingBo);

                w2RecordsBo.setKskm("2");
                recordsDataList.add(w2RecordsBo);
            }
            if(extraData.getRow2().equals("1")){
                w2QueuingBo.setKskm("3");
                dataList.add(w2QueuingBo);

                w2RecordsBo.setKskm("3");
                recordsDataList.add(w2RecordsBo);
            }


        }

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        queuingService.handleImportData(dataList,recordsDataList);

    }
}
