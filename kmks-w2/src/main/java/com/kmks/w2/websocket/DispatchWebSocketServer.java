package com.kmks.w2.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.kmks.w2.websocket.map.DispatchDataMap;
import com.kmks.w2.websocket.map.DispatchDataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/dispatch/{userId}")
public class DispatchWebSocketServer {

    //与某个客户端的连接会话，通过此会话对象给客户端发送数据
    public Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    //注：泛型是当前类名
    //private static Set<WsMessageService> webSockets = new CopyOnWriteArraySet<>();
//    private static Map<String, DispatchWebSocketServer> dispatchSocketBeanMap = new ConcurrentHashMap<>();

    //用来保存在线连接数
    //private static Map<String, Session> sessionPool = new HashMap<>();

    //每次连接都是一个新的会话对象，线程安全的
    String userId;


    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        this.userId = userId;
        DispatchDataMap.addDispatchSocketsBeanMap(userId,this);
        log.info("调度中心连接成功，userId：{}，当前在线人数：{}", userId, this.getOnLineCount());
    }


    @OnMessage
    public void onMessage(String message) throws IOException {
        if (ObjectUtil.isNull(session) || !session.isOpen()) {
            return;
        }
        DispatchDataMap.addQueryStringMap(this.userId,message);

//        log.info("收到客户端的消息:" + message);
//        session.getBasicRemote().sendText("收到");
    }

    @OnClose
    public void onClose() throws IOException {
        log.info("调度中心会话关闭，关闭会话的用户Id为：{}", this.userId);
        DispatchDataMap.deleteData(this.userId);
        log.info("当前在线人数：{}", this.getOnLineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("连接错误：" + error.getMessage());
        error.printStackTrace();
    }


    /**
     * <p>返回在线人数</p>
     *
     * @author hkl
     * @date 2023/2/16
     */
    private int getOnLineCount() {
        return DispatchDataMap.getDispatchSocketsBeanMap().size();
    }

    //实现服务器主动推送
    public void sendMessage(String message) {
        DispatchDataMap.getDispatchSocketsBeanMap().forEach((key,value)->{
            try {
                value.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //实现服务器主动推送
    public void sendMessage(String message,String userId) {
        DispatchDataMap.getDispatchSocketsBeanMap().forEach((key,value)->{
            if(key.equals(userId)){
                try {
                    value.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
