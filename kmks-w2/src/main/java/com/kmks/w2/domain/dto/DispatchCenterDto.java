package com.kmks.w2.domain.dto;

import lombok.Data;

/**
 * 调度中心数据
 * @author Star
 * @description: TODO
 * @date 2024/6/6 9:09
 */
@Data
public class DispatchCenterDto {
    /**
     * 考车编号
    **/
    private String kcbh;

    /**
     * 考车信息
     **/
    private String kcxx;

    /**
     * 考试状态
     **/
    private String kszt;

    /**
     * 考生姓名
     **/
    private String ksxm;

    /**
     * 证件号码
     **/
    private String zjhm;

    /**
     * 当前项目
     **/
    private String dqxm;

    /**
     * 上传状态
     **/
    private String sczt;

    /**
     * 考试分数
     **/
    private Long ksfs;

    /**
     * 考试成绩
     **/
    private String kscj;

    /**
     * 考试次数
     **/
    private Long kscs;

    /**
     * 里程
     **/
    private String lc;

    /**
     * 车速
     **/
    private String cs;

    /**
     * 状态
     **/
    private Integer zt;

}
