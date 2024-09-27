package com.kmks.jianguanold.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试场地备案信息下载
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C01Vo {

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

        // 发证机关
        @ExcelProperty(value = "发证机关")
        private String fzjg;

        // 管理部门
        @ExcelProperty(value = "管理部门")
        private String glbm;

        // 考试科目
        @ExcelProperty(value = "考试科目")
        private String kskm;

        // 考场名称
        @ExcelProperty(value = "考场名称")
        private String kcmc;

        // 考场代码
        @ExcelProperty(value = "考场代码")
        private String kcdddh;

        // 适用准驾车型范围
        @ExcelProperty(value = "适用准驾车型范围")
        private String kkcx;

        // 适用业务类型范围
        @ExcelProperty(value = "适用业务类型范围")
        private String ywlx;

        // 总队验收日期
        @ExcelProperty(value = "总队验收日期")
        private String zdysrq;

        // 验收人
        @ExcelProperty(value = "验收人")
        private String ysr;

        // 科目二预约模式
        @ExcelProperty(value = "科目二预约模式")
        private String kmeyyms;

        // 分组模式
        @ExcelProperty(value = "分组模式")
        private String fzms;

        // 考试人数限制
        @ExcelProperty(value = "考试人数限制")
        private String kmeksrsxz;

        // 科目二桩考人数限制
        @ExcelProperty(value = "科目二桩考人数限制")
        private String kmezkrsxz;

        // 科目二场考人数限制
        @ExcelProperty(value = "科目二场考人数限制")
        private String kmeckrsxz;

        // 桩考评判方式
        @ExcelProperty(value = "桩考评判方式")
        private String zksfdz;

        // 场考评判方式
        @ExcelProperty(value = "场考评判方式")
        private String cksfdz;

        // 桩考开始联网时间
        @ExcelProperty(value = "桩考开始联网时间")
        private String zklwrq;

        // 场考开始联网时间
        @ExcelProperty(value = "场考开始联网时间")
        private String cklwrq;

        // 使用状态
        @ExcelProperty(value = "使用状态")
        private String kczt;

        // 桩考设备数
        @ExcelProperty(value = "桩考设备数")
        private String zksbs;

        // 场考设备数
        @ExcelProperty(value = "场考设备数")
        private String cksbs;

        // 创建日期
        @ExcelProperty(value = "创建日期")
        private String cjsj;

        // 更新日期
        @ExcelProperty(value = "更新日期")
        private String gxsj;

    }
}
