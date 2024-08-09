package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评判参数业务对象 w2_kfconfig
 *
 * @author Lynn
 * @date 2023-03-21
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class W2KfconfigBo{

    /**
     * 唯一标识
     */
    @NotBlank(message = "唯一标识不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("唯一标识")
    private String gakey;

    /**
     * 项目名称
     */
    @Column("项目名称")
    private String gakfmc;

    /**
     * 项目序号
     */
    @Column("项目序号")
    private Long xmxh;

    /**
     * 项目扣分说明
     */
    @Column("项目扣分说明")
    private String kfmc;

    /**
     * 参数值
     */
    @Column("参数值")
    private String value;

    /**
     * 参数说明
     */
    @Column("参数说明")
    private String beizhu;

    /**
     * 考试科目
     */
    @Column("考试科目")
    private String kskm;

    /**
     * 扣分代码
     */
    @Column("扣分代码")
    private String gakfdm;

    /**
     * 评判方式
     */
    @Column(value = "评判方式",dict = "param_judge_type")
    private String autoflag;

    /**
     * 考试项目
     */
    @Column("考试项目")
    private String ksxm;

    /**
     * 项目代码
     */
    @Column("项目代码")
    private Long ksxmdm;

    /**
     * 扣分值
     */
    @Column("扣分值")
    private Long kf;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "唯一标识不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("ID")
    private String id;

    /**
     * 分类标记
     */
    @Column(value = "菜单分类",dict = "param_judge_menu")
    private String paramtype;

    /**
     * $column.columnComment
     */
    private String autoflag1;

    /**
     * $column.columnComment
     */
    private String xmtype;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
