package com.kmks.w2.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Star
 * @description: 自定义config属性
 * @date 2023/4/6 14:14
 */
@Component
@Data
@com.ruoyi.common.annotation.Config(prefix = "kmks")
public class ConfigProperties {
    private boolean  showDiffPlace;

    private int k2PassScore;
    private int k3PassScore;
// 打印成绩模板
    private String printTpl;
//    是否有夜考 -1 标准，0日考，1夜考
    private int sfyk;


}
