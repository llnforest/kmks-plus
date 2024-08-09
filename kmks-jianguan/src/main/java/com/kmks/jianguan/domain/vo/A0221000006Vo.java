package com.kmks.jianguan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
@AllArgsConstructor
public class A0221000006Vo {
    // 考试监管系统时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sj;
}
