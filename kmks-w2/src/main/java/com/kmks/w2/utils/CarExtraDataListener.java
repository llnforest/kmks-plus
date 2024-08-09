package com.kmks.w2.utils;

import cn.hutool.core.lang.UUID;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.domain.bo.W2CarModelBo;
import com.kmks.w2.service.IW2CarModelService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarExtraDataListener implements ReadListener<ExtraData> {

    private IW2CarModelService iw2CarModelService;

    W2CarModelBo bo = new W2CarModelBo();
    List<ExtraData> cachedDataList = new ArrayList<>();

    public CarExtraDataListener(IW2CarModelService iw2CarModelService) {
        this.iw2CarModelService = iw2CarModelService;
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
        if (cachedDataList.size() > 4) {
            //设置考车类型
            bo.setModelname(cachedDataList.get(0).getRow2());
            //设置项目名称
            bo.setCph(cachedDataList.get(1).getRow2());
            //设置坐标格式
            bo.setModeltype(cachedDataList.get(2).getRow2());
        }
        if (cachedDataList.size() > 5) {
            StringBuilder pointData = new StringBuilder();
            pointData.append(cachedDataList.get(3).getRow2()).append(";");
//            pointData.append(cachedDataList.get(4).getRow2()).append(";");
            for (int i = 4; i < cachedDataList.size(); i++) {
                if (i == 6) {
                    continue;
                }
                if(i >= 9 ){
                    pointData.append(cachedDataList.get(i).getRow2())
                            .append(",")
                            .append(cachedDataList.get(i).getRow3())
                            .append(";");
                }else{
                    pointData.append(cachedDataList.get(i).getRow2()).append(",").append(cachedDataList.get(i).getRow3()).append(",").append(cachedDataList.get(i).getRow4()).append(";");
                }

            }
            log.info(pointData.toString());
            bo.setPointdata(pointData.toString());
        }

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        iw2CarModelService.insertByBo(bo);
        log.info("存储数据库成功！");
    }


}
