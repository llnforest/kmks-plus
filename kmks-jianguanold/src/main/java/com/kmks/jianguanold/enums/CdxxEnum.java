package com.kmks.jianguanold.enums;

import com.kmks.jianguanold.domain.bo.*;
import com.kmks.jianguanold.domain.vo.*;
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
    A17C01(A17C01Bo.class, A17C01Vo.class, "17C01"),
    A17C02(A17C02Bo.class, A17C02Vo.class, "17C02"),
    A17C04(A17C04Bo.class, A17C04Vo.class, "17C04"),
    A17C05(A17C05Bo.class, A17C05Vo.class, "17C05"),
    A17C06(A17C06Bo.class, A17C06Vo.class, "17C06"),
    A17C07(A17C07Bo.class, A17C07Vo.class, "17C07"),
    A17C08(A17C08Bo.class, A17C08Vo.class, "17C08"),
    A17C09(A17C09Bo.class, A17C09Vo.class, "17C09"),
    A17C10(A17C10Bo.class, A17C10Vo.class, "17C10"),
    A17C51(A17C51Bo.class, A17C51Vo.class, "17C51"),
    A17C52(A17C52Bo.class, A17C52Vo.class, "17C52"),
    A17C53(A17C53Bo.class, A17C53Vo.class, "17C53"),
    A17C54(A17C54Bo.class, A17C54Vo.class, "17C54"),
    A17C55(A17C55Bo.class, A17C55Vo.class, "17C55"),
    A17C56(A17C56Bo.class, A17C56Vo.class, "17C56"),
    A17C57(A17C57Bo.class, A17C57Vo.class, "17C57"),
    A17C58(A17C58Bo.class, A17C58Vo.class, "17C58"),
    A17C59(A17C59Bo.class, A17C59Vo.class, "17C59"),
    A17C60(A17C60Bo.class, A17C60Vo.class, "17C60"),
    A17CB2(A17CB2Bo.class, A17CB2Vo.class, "17CB2"),
    A17CB3(A17CB3Bo.class, A17CB3Vo.class, "17CB3"),
    A17CB4(A17CB4Bo.class, A17CB4Vo.class, "17CB4"),
    A17CC1(A17CC1Bo.class, A17CC1Vo.class, "17CC1");

    private final Class clazz;
    private final Class clazzVo;
    private final String jkid;

    public static CdxxEnum findByClazz(Class clazz) {
        for (CdxxEnum type : values()) {
            if (type.getClazz() == clazz) {
                return type;
            }
        }
        return null;
    }
}
