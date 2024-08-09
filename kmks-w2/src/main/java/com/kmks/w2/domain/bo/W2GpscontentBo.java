package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轨迹数据业务对象 w2_gpscontent
 *
 * @author ruoyi
 * @date 2023-04-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2GpscontentBo extends BaseEntity {

    /**
     * 车载时间戳
     */
    @NotNull(message = "车载时间戳不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long timeid;

    /**
     * gps报文1
     */
    @NotBlank(message = "gps报文1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gpscon1;

    /**
     * gps报文2
     */
    @NotBlank(message = "gps报文2不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gpscon2;

    /**
     * 车辆信号报文
     */
    @NotBlank(message = "车辆信号报文不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sigcon;

    /**
     * 考试过程报文
     */
    @NotBlank(message = "考试过程报文不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscon;

    /**
     * 发送日期
     */
    @NotNull(message = "发送日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sendrq;

    private Long sendrqStart;
    private Long sendrqEnd;

    /**
     * 表标记
    **/
    private String tableMark;

    /**
     * 车号
     */
    @NotBlank(message = "车号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String carno;

    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfzhm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String djc;

    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;

    /**
     * 流水号
     */
    @NotBlank(message = "流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lsh;

    /**
     * 考车号牌
     */
    @NotBlank(message = "考车号牌不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kchp;


}
