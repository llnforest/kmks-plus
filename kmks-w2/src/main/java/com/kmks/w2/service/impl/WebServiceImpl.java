package com.kmks.w2.service.impl;

import com.kmks.w2.service.WebService;
import com.ruoyi.common.webservice.WebServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/25 17:21
 */
@Service
@Slf4j
public class WebServiceImpl implements WebService {


    /**
     * 下载信息
     *
     * @param cmd cmd
     * @return {@link String}
     */
    @Override
    public String downUpInfo(String cmd){
        HashMap<String, String> map = new HashMap<>();
        map.put("StrInfo",cmd);
        String downUpInfo = WebServiceUtil.send("DownUpInfo", map);
        try{
            String[] split = downUpInfo.split(";");
            if(split[2].equals("1")){
                return split[3];
            }
            log.info("返回数据:{}",downUpInfo);
        }catch (Exception exception){
            log.info("数据处理失败：{}",exception.getMessage());
        }

        return null;
    }



    /**
     * 下载考点信息
     * @return {@link String}
     */
    @Override
    public String Down17C01Xml(){
        String info = WebServiceUtil.send("Down17C01Xml");
        return info;
    }

    /**
     * 下载项目、备案信息
     * @return {@link String}
     */
    @Override
    public String Down17C02Xml(){
        String info = WebServiceUtil.send("Down17C02Xml");
        return info;
    }

    /**
     * 下载车辆信息
     * @return {@link String}
     */
    @Override
    public String Down17C03Xml(){
        String info = WebServiceUtil.send("Down17C03Xml");
        return info;
    }

    /**
     * 下载考官信息
     * @return {@link String}
     */
    @Override
    public String Down17C04Xml(){
        String info = WebServiceUtil.send("Down17C04Xml");
        return info;
    }

    /**
     * 下载驾校信息
     * @return {@link String}
     */
    @Override
    public String Down17C05Xml(){
        String info = WebServiceUtil.send("Down17C05Xml");
        return info;
    }

    /**
     * 下载明细、分组信息
     * @return {@link String}
     */
    @Override
    public String Down17C06Xml(String ksrq){
        HashMap<String, String> map = new HashMap<>();
        map.put("ksrq",ksrq);
        String info = WebServiceUtil.send("Down17C06Xml",map);
        return info;
    }

    /**
     * 查询、下载信息
     * @return {@link String}
     */
    @Override
    public String Down17C08Xml(String ksrq, String zpdownflag){
        HashMap<String, String> map = new HashMap<>();
        map.put("ksrq",ksrq);
        map.put("zpdownflag",zpdownflag);
        String info = WebServiceUtil.send("Down17C08Xml",map);
        return info;
    }

    /**
     * 同步时间
     * @return {@link String}
     */
    @Override
    public String Down17C09Xml(){
        String info = WebServiceUtil.send("Down17C09Xml");
        return info;
    }

    /**
     * 同步时间
     * @return {@link String}
     */
    @Override
    public String ReExamine(String cmd){
        HashMap<String, String> map = new HashMap<>();
        map.put("StrInfo",cmd);
        String info = WebServiceUtil.send("ReExamine",map);
        return info;
    }
}
