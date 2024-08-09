package com.kmks.w2.service;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/25 17:20
 */
public interface WebService {
    String downUpInfo(String cmd);

    String Down17C01Xml();

    String Down17C02Xml();

    String Down17C03Xml();

    String Down17C04Xml();

    String Down17C05Xml();

    String Down17C06Xml(String ksrq);

    String Down17C08Xml(String ksrq, String zpdownflag);

    String Down17C09Xml();

    String ReExamine(String cmd);
}
