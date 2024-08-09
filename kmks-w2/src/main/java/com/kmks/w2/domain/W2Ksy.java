package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考官信息对象 w2_ksy
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = false)
@TableName("w2_ksy")
public class W2Ksy{

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "xh")
    private String xh;
    /**
     * 所属发证机关
     */
    private String sszd;
    /**
     * 管理部门
     */
    private String glbm;
    /**
     * 证件名称
     */
    private String sfzmmc;
    /**
     * 身份证明号码
     */
    private String sfzmhm;
    /**
     * 驾驶证档案编号
     */
    private String dabh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别
     */
    private String xb;
    /**
     * 出生日期
     */
    private String csrq;
    /**
     * 考试准驾车型范围
     */
    private String zkcx;
    /**
     * 考试员证发证日期
     */
    private String fzrq;
    /**
     * 考试员证有效期止
     */
    private String kszyxqz;
    /**
     * 考试员证状态
     */
    private String ksyzt;
    /**
     * 工作单位
     */
    private String gzdw;
    /**
     * 经办人
     */
    private String jbr;
    /**
     * 考试员证发证单位
     */
    private String fzjg;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 更新时间
     */
    private String gxsj;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
