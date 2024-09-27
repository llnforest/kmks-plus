package com.kmks.jianguanold.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 驾校备案信息下载
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C05Vo {
    private Head head;

    private List<Body> body;

    @Data
    public static class Head {
        // 标记 1成功
        private String code;

        // 提示或错误描述信息
        private String message;

        // 记录条数
        private Integer rownum;
    }

    @Data
    @ExcelIgnoreUnannotated
    public static class Body {
        // 序号
        @ExcelProperty(value = "序号")
        private String xh;

        // 驾校名称
        @ExcelProperty(value = "驾校名称")
        private String jxmc;

        // 驾校简称
        @ExcelProperty(value = "驾校简称")
        private String jxjc;

        // 驾校代码
        @ExcelProperty(value = "驾校代码")
        private String jxdm;

        // 驾校地址
        @ExcelProperty(value = "驾校地址")
        private String jxdz;

        // 联系电话
        @ExcelProperty(value = "联系电话")
        private String lxdh;

        // 联系人
        @ExcelProperty(value = "联系人")
        private String lxr;

        // 法人代表
        @ExcelProperty(value = "法人代表")
        private String frdb;

        // 注册资金
        @ExcelProperty(value = "注册资金")
        private String zczj;

        // 驾校级别
        @ExcelProperty(value = "驾校级别")
        private String jxjb;

        // 培训准驾车型
        @ExcelProperty(value = "培训准驾车型")
        private String kpxcx;

        // 所属发证机关
        @ExcelProperty(value = "所属发证机关")
        private String fzjg;

        // 驾校状态
        @ExcelProperty(value = "驾校状态")
        private String jxzt;

        // 审核人
        @ExcelProperty(value = "审核人")
        private String shr;

        // 创建时间
        @ExcelProperty(value = "创建时间")
        private String cjsj;

        // 更新时间
        @ExcelProperty(value = "更新时间")
        private String gxsj;
    }
}
