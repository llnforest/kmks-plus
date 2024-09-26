package com.kmks.w2.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 分车DTO对象
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
public class SplitCarDto {

    private static final long serialVersionUID = 1L;


    /**
     * 考车号对应数据Map
     */
    private Map<String, Integer> kchMap;

    /**
     * 车牌号码List
     */
    private List<String> cphList;

    /**
     * 分车人数
     */
    private Long splitNum;


    /**
     * 线路配置
     */
    private Map<String, Long> lineConfigMap;

    /**
     * 考车信息Map
     **/
    private Map<String, W2KcxxVo> kcxxMap;

    /**
     * 考官证件号
     **/
    private String kgzjh;

    /**
     * 考试场次
     **/
    private Long kscc;


}
