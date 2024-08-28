package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.ibatis.type.JdbcType;

/**
 * 地图模型业务对象 w2_fieldmap
 *
 * @author ghgd
 * @date 2023-03-15
 */

@Data
public class W2FieldmapBo implements Serializable {

    /**
     * 主键ID
     */
    @Column(value = "主键")
    private String id;

    /**
     * 模型名称
     */
    @NotBlank(message = "模型名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "模型名称")
    private String fieldname;

    /**
     * 项目编号
     */
    @NotBlank(message = "项目编号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "项目编号")
    private String fieldid;

    /**
     * 项目类型
     */
    @NotBlank(message = "项目类型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "项目类型")
    private String fieldtype;

    /**
     * 项目点数量
     */
    @NotBlank(message = "项目点数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "项目点数量")
    private String pointcount;

    /**
     * 语音备注
     */
    private String state;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "状态")
    private String state1;

    /**
     * 线路
     */
    @Column(value = "线路")
    private String lineno;

    /**
     * 位置数量
     */
    @NotBlank(message = "位置数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "位置数量")
    private String pointposition;

    /**
     * 加密CODE
     */
    private String scode;


    /**
     * 考场名称
     */
    @NotBlank(message = "考场名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "考场名称")
    private String schoolname;

    /**
     * 项目名称
     */
    @Column(value = "项目名称")
    private String projectname;

    /**
     * 场模标记
     */
    @NotNull(message = "场模标记不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "场模标记")
    private Long relationId;

    /**
     * 导入点数据集合
     */
    @NotBlank(message = "导入点数据集合不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "导入点数据")
    private String temppointdata;

    /**
     * 点数据集合
     */
    @NotBlank(message = "点数据集合不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "导出点数据")
    private String pointdata;


    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.VARCHAR)
    @Column(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.DATE)
    @Column(value = "创建时间")
    private Date createTime;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
