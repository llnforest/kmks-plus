package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 导入模板
 */
@Getter
@AllArgsConstructor
public enum XlsxTplType {

    /**
     * 车辆合码器导入模板
     */
    DEPT("car","TplConfigCar.xlsx", "车辆合码器导入模板.xlsx"),

    /**
     * 设备合码器导入模板
     */
    DEPT_AND_CHILD("device","TplConfigDevice.xlsx", "设备合码器导入模板.xlsx"),

    /**
     * 项目监控导入模板
     */
    SELF("switch","TplConfigSwitch.xlsx", "项目监控导入模板.xlsx");

    private final String code;

    private final String filename;

    private final String outFilename;


    public static XlsxTplType findCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (XlsxTplType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
