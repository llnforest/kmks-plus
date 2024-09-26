package com.kmks.jianguan.enums;

import com.kmks.jianguan.domain.bo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:14
 */
@Getter
@AllArgsConstructor
public enum CdxxEnum {
    A17C01(A0221000001Bo.class,"02-21-000001","查询类"),
    A0221000002(A0221000002Bo.class,"02-21-000002","查询类"),
    A0221000003(A0221000003Bo.class,"02-21-000003","查询类"),
    A0221000004(A0221000004Bo.class,"02-21-000004","写入类"),
    A0221000005(A0221000005Bo.class,"02-21-000005","查询类"),
    A0221000006(A0221000006Bo.class,"02-21-000006","查询类"),
    A0221000007(A0221000007Bo.class,"02-21-000007","写入类"),
    A0221000008(A0221000008Bo.class,"02-21-000008","查询类"),
    A0221000009(A0221000009Bo.class,"02-21-000009","写入类"),
    A0221000010(A0221000010Bo.class,"02-21-000010","写入类"),
    A0221000011(A0221000011Bo.class,"02-21-000011","写入类"),
    A0221000012(A0221000012Bo.class,"02-21-000012","写入类"),
    A0221000013(A0221000013Bo.class,"02-21-000013","写入类"),
    A0221000014(A0221000014Bo.class,"02-21-000014","写入类"),
    A0221000015(A0221000015Bo.class,"02-21-000015","写入类");

    private final Class clazz;
    private final String sjbs;
    private final String czlx;

    public static CdxxEnum findByClazz(Class clazz) {
        for (CdxxEnum type : values()) {
            if (type.getClazz() == clazz) {
                return type;
            }
        }
        return null;
    }
}
