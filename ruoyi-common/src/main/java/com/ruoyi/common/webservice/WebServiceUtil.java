package com.ruoyi.common.webservice;

import com.ruoyi.common.config.WebServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/6 15:09
 */
@Slf4j
public class WebServiceUtil {
    /**
     * 发送请求
     * @Param [methodName]
     * @return java.lang.String
     * @Author lynn 17:31 2023/5/6
     **/
    public static String send(String methodName){
        return send(methodName,new HashMap<>());
    }

    /**
     * 发送请求
     * @Param [methodName, paramMap]
     * @return java.lang.String
     * @Author lynn 17:31 2023/5/6
    **/
    public static String send(String methodName,Map<String,String> paramMap){

        try {
            //实例化访问对象
            Service service = new Service();
            //实例化调用对象
            Call call = (Call) service.createCall();
            //在调用对象中添加WebService地址
            call.setTargetEndpointAddress(new java.net.URL(WebServiceConfig.url));

            if(!paramMap.containsKey("Key")) paramMap.put("Key",WebServiceConfig.key);
            paramMap.forEach((k,v)->{
                QName qn = new QName(WebServiceConfig.targetNamespace, k);
                call.addParameter(qn, XMLType.XSD_STRING, ParameterMode.IN);
            });

            //设置返回值格式（字符串或者组装对象）
            call.setReturnType(XMLType.XSD_STRING);
            //是否是SOAPAction这里需要看WebService是否要求如下格式，如果没有要求可以不添加此设置
            call.setUseSOAPAction(true);
            //如果前面要求是SOAPAction的话需要添加下面设置，指定访问那个命名空间上的那个方法
            call.setSOAPActionURI("");
            //在调用对象中添加WebService对应的命名空间，以及将要调用的函数名
            call.setOperationName(new QName(WebServiceConfig.targetNamespace, methodName));
            //调用目标方法,设置参数值将返回值转换为String类型
            String ret = (String) call.invoke(paramMap.values().toArray());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("webservice请求失败：方法：{},参数：{},原因：{}",methodName,paramMap,e.getMessage());
        }
        return null;
    }

//    public static String send(ClientBean clientBean){
//
//        try {
//            Object[] paramValues;
//            //实例化访问对象
//            Service service = new Service();
//            //实例化调用对象
//            Call call = (Call) service.createCall();
//            //在调用对象中添加WebService地址
//            call.setTargetEndpointAddress(new java.net.URL(WebServiceConfig.url));
//
//
//            paramValues = new Object[clientBean.getClientFields().size()];
//
//            int i = 0;
//            //设置入参，第一个参数是命名空间以及参数名，这两个参数是采用一个Qname变量打包传入的，第二个参数是入参的类型（字符或者数字）第三个参数是入参种类
//            for (ClientField field : clientBean.getClientFields()) {
//                QName qn = new QName(WebServiceConfig.targetNamespace, field.getParamName());
//                if (field.getParamType() == String.class) {
//                    call.addParameter(qn, XMLType.XSD_STRING, ParameterMode.IN);
//                } else if (field.getParamType() == int.class) {
//                    call.addParameter(qn, XMLType.XSD_INT, ParameterMode.IN);
//                } else if (field.getParamType() == Integer.class) {
//                    call.addParameter(qn, XMLType.XSD_INTEGER, ParameterMode.IN);
//                } else if (field.getParamType() == Double.class) {
//                    call.addParameter(qn, XMLType.XSD_DOUBLE, ParameterMode.IN);
//                } else if (field.getParamType() == Float.class) {
//                    call.addParameter(qn, XMLType.XSD_FLOAT, ParameterMode.IN);
//                } else if (field.getParamType() == Long.class) {
//                    call.addParameter(qn, XMLType.XSD_LONG, ParameterMode.IN);
//                } else {
//                    throw new Exception("请确定传入参数类型是否正确，或者该参数类型还没有被注册。");
//                }
//                //获取参数值
//                paramValues[i] = field.getParamValue();
//                i++;
//            }
//            //设置返回值格式（字符串或者组装对象）
//            call.setReturnType(XMLType.XSD_STRING);
//            //是否是SOAPAction这里需要看WebService是否要求如下格式，如果没有要求可以不添加此设置
//            call.setUseSOAPAction(true);
//            //如果前面要求是SOAPAction的话需要添加下面设置，指定访问那个命名空间上的那个方法
//            call.setSOAPActionURI(clientBean.getActionUrl());
//            //在调用对象中添加WebService对应的命名空间，以及将要调用的函数名
//            call.setOperationName(new QName(WebServiceConfig.targetNamespace, clientBean.getMethodName()));
//            String ret;
//            //调用目标方法,设置参数值将返回值转换为String类型
//            ret = (String) call.invoke(paramValues);
//            return ret;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
