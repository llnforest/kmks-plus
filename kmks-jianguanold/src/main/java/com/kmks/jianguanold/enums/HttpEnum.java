package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:14
 */
@Getter
@AllArgsConstructor
public enum HttpEnum {
    //成功
    SUCCESS(200,"success"),
    //失败
    FAIL(500,"fail");
    private final int code;
    private final String message;

    public static HttpEnum findByCode(int code) {
        for (HttpEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
