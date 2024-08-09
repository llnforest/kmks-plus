package com.kmks.w2.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.XmlUtil;
import com.kmks.jianguan.domain.bo.A0221000001Bo;
import com.kmks.jianguan.domain.vo.A0221000001Vo;
import com.kmks.jianguan.service.IJgService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.webservice.WebServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 车辆合码器
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController extends BaseController {

    @Autowired
    private IJgService IJgService;

    @GetMapping("/sendhttp")
    public void test(){
        A0221000001Bo a0221000001Bo = new A0221000001Bo();
        a0221000001Bo.setSfzmhm("343232111");
        A0221000001Vo a0221000001Vo = IJgService.a0221000001(a0221000001Bo, "abc", "abc");
        log.info("resutl:{}",a0221000001Vo);
    }
    @GetMapping("/webService")
    public String testWebService(){
        HashMap<String, String> map = new HashMap<>();
//        String result = WebServiceUtil.send("GetDt");
//        map.put("kcbh","05");
//        String result = WebServiceUtil.send("GetVersion",map);
        map.put("sfzhm","150426199307102159");
//        String result = WebServiceUtil.send("QueryUser",map);
        map.put("StrInfo","select * from w2_system where nid = 54");
        String result = WebServiceUtil.send("QryPublic",map);
        ArrayList<Map<String, String>> tables = (ArrayList<Map<String, String>>) XmlUtil.xmlToMap(result).get("Tables");

        log.info("xml转Map:{}",tables.get(0).get("PARAMNAME"));;

        if(ObjectUtil.isNull(result)){
            log.info("失败，结果为null");
        }else{
            log.info("成功");
        }
        log.info("结果：{}",result);
        return result;
    }



}
