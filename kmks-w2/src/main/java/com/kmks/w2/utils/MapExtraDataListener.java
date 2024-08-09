package com.kmks.w2.utils;

import cn.hutool.core.lang.UUID;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.domain.bo.W2FieldmapBo;
import com.kmks.w2.service.IW2FieldmapService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MapExtraDataListener implements ReadListener<ExtraData> {

    private  IW2FieldmapService iW2FieldmapService;

    W2FieldmapBo bo = new W2FieldmapBo();
    List<ExtraData> cachedDataList = new ArrayList<>();

    public MapExtraDataListener(IW2FieldmapService iW2FieldmapService) {
        this.iW2FieldmapService = iW2FieldmapService;
    }

    @Override
    public void invoke(ExtraData data, AnalysisContext context) {
        log.info("ExtraData" + data);
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        dealCachedData();
        saveData();
    }

    private void dealCachedData() {
        if (cachedDataList == null) {
            return;
        }
        bo.setId(UUID.randomUUID().toString());
        if (cachedDataList.size() > 0) {
            StringBuilder temppointdata = new StringBuilder();
            for (int i = 0; i < cachedDataList.size(); i++) {
                temppointdata.append(cachedDataList.get(i).getRow1()).append(":").append(cachedDataList.get(i).getRow2()).append(":").append(cachedDataList.get(i).getRow3()).append(":").append(cachedDataList.get(i).getRow4()).append(";");
            }
            bo.setTemppointdata(temppointdata.toString());
        }

        if (cachedDataList.size()> 4){
            //设置地图名称
            bo.setSchoolname(cachedDataList.get(0).getRow2());
            //设置项目名称
            bo.setFieldname(cachedDataList.get(1).getRow2());
            //设置坐标格式
            bo.setFieldtype(cachedDataList.get(2).getRow2());
            //设置项目编号
            bo.setFieldid(cachedDataList.get(3).getRow2());
        }
        if (cachedDataList.size() > 5){
            StringBuilder stringBuffer = new StringBuilder();
            int count = 0;
            for (int i = 4; i < cachedDataList.size(); i++){
                count++;
                stringBuffer.append(cachedDataList.get(i).getRow2()).append(",").append(cachedDataList.get(i).getRow3()).append(",").append(cachedDataList.get(i).getRow4()).append(";");
            }
            log.info(stringBuffer.toString());
            bo.setPointdata(stringBuffer.toString());
            bo.setPointcount(String.valueOf(count));
        }

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        iW2FieldmapService.insertByBo(bo);
        log.info("存储数据库成功！");
    }


}
