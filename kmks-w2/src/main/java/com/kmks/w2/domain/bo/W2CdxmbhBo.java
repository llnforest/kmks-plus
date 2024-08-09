package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.CustomGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import javax.validation.constraints.*;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 场地项目编号业务对象 w2_cdxmbh
 *
 * @author ruoyi
 * @date 2023-03-28
 */

@Data
public class W2CdxmbhBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class, CustomGroup.class})
    private Long nid;

    /**
     * 考试项目
     */
    @NotNull(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class, CustomGroup.class })
    private Long paramdm;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paramname;

    /**
     * 信息
     */
    private String msg;

    /**
     * 考试状态
     */
    private String kszt;

    /**
     * 中心IP
     */
    private String zxip;

    /**
     * 端口
     */
    private String portms;

    /**
     * 内部代码
     */
    @NotBlank(message = "内部代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mdm;

    /**
     * 设备序号
     */
    @NotBlank(message = "设备序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sbxh;

    /**
     * 公安代码
     */
    @NotBlank(message = "公安代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gadm;

    /**
     * 考试科目
     */
    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;

    /**
     * 项目IP地址
     */
    private String xmipaddress;

    /**
     * 项目IP端口
     */
    private String xmipport;

    /**
     * 项目IP用户
     */
    private String xmipuser;

    /**
     * 项目IP密码
     */
    private String xmippwd;

    /**
     * 项目IP通道
     */
    private String xmipchanel;

    /**
     * 使用状态
     */
    private String syzt;

    /**
     * 序号
     */
    private String xh;

    /**
     * zzcs
     */
    private String zzcs;


}
