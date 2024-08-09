package com.kmks.w2.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计扣分代码
 *
 * @author ruoyi
 * @date 2023-05-24
 */

@Data
public class AnalyseKfdmBo {

    /**
     *  驾校代码
     */
    private String jxdm;

    /**
     *  考试车型
     */
    private String kscx;

    /**
     *  考试原因
     */
    private String ksyy;

    /**
     * 考试项目
     */
    @NotBlank(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * 扣分代码
     */
    @NotBlank(message = "扣分代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kfdm;

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sbbh;


    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;


    /**
     * 公安代码
     */
    @NotBlank(message = "公安代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gadm;



    /**
     * 考试员1
     */
    @NotBlank(message = "考试员1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy;



    /**
     * 添加日期
     */
    @NotNull(message = "添加日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date addtime;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
