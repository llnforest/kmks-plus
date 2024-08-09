package com.kmks.w2.websocket.map;

import com.kmks.w2.websocket.DispatchWebSocketServer;
import com.kmks.w2.websocket.DispatchWebSocketServer;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度socket数据
 * @author Star
 * @description: TODO
 * @date 2024/5/27 14:59
 */
@Data
public class DispatchDataMap {
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    //注：泛型是当前类名
    //private static Set<WsMessageService> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, DispatchWebSocketServer> DispatchSocketsBeanMap = new HashMap<>();

    // 查询数据
    public static  Map<String,String> queryStringMap = new HashMap<>();

    public static Map<String, DispatchWebSocketServer> getDispatchSocketsBeanMap(){
        return DispatchSocketsBeanMap;
    }

    public static DispatchWebSocketServer getDispatchSocketsBeanMap(String key){
        return DispatchSocketsBeanMap.get(key);
    }

    public static DispatchWebSocketServer addDispatchSocketsBeanMap(String key,DispatchWebSocketServer DispatchSocketsBean){
        return DispatchSocketsBeanMap.put(key,DispatchSocketsBean);
    }
    public static DispatchWebSocketServer delDispatchSocketsBeanMap(String key){
        return DispatchSocketsBeanMap.remove(key);
    }

    //------------------------------查询数据----------------------------------

    public static Map<String, String> getQueryStringMap(){
        return queryStringMap;
    }

    public static String getQueryStringMap(String key){
        return queryStringMap.get(key);
    }

    public static String addQueryStringMap(String key,String queryString){
        return queryStringMap.put(key,queryString);
    }

    public static String delQueryStringMap(String key){
        return queryStringMap.remove(key);
    }

    public static void deleteData(String key){
        delQueryStringMap(key);
        delDispatchSocketsBeanMap(key);
    }
}
