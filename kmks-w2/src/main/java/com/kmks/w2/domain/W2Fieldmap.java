package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.ibatis.type.JdbcType;

/**
 * 地图模型对象 w2_fieldmap
 *
 * @author ghgd
 * @date 2023-03-15
 */
@Data
@TableName("w2_fieldmap")
public class W2Fieldmap  implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID")
    private String id;

    /**
     * 项目名称
     */

    private String fieldname;
    /**
     * 项目编号
     */

    private String fieldid;
    /**
     * 项目类型
     */

    private String fieldtype;
    /**
     * 项目点数量
     */
    private String pointcount;
    /**
     * 状态
     */
    private String state;
    /**
     * 线路
     */
    private String lineno;
    /**
     * 位置数量
     */
    private String pointposition;
    /**
     * 加密CODE
     */
    private String scode;

    /**
     * 考场名称
     */
    private String schoolname;
    /**
     * 项目名称
     */
    private String projectname;
    /**
     * 导入点数据集合
     */
    private String temppointdata;
    /**
     * 点数据集合
     */
    private String pointdata;

    /**
     * 车模标记
     */
    private Long relationId;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.VARCHAR)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.DATE)
    private Date createTime;

}
