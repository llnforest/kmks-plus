package com.kmks.w2.domain.gateDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 考试结果
 * @author Star
 * @description: TODO
 * @date 2024/6/6 9:09
 */
@Data
public class ScoreResultDto {
    private static final long serialVersionUID = 1L;
    /**
     * 考试成绩（排队）
     **/
    private Long kscj;

    /**
     * 考试结果（成绩）
    **/
    private String ksjg;

    /**
     * 第一次考试结果（成绩）
     **/
    private String kscj1;

    /**
     * 第二次考试结果（成绩）
     **/
    private String kscj2;

}
