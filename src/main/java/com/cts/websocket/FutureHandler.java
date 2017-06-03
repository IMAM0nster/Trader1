package com.cts.websocket;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by fy on 2017/6/2.
 */
public class FutureHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger("Future");

    static Queue<WebSocketSession> sessions = new ConcurrentLinkedQueue<WebSocketSession>();

    static Map<WebSocketSession, Long> subscribes = new ConcurrentHashMap<WebSocketSession, Long>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        // 收到具体期货的请求时，浏览器会发送两条消息，一条直接向我请求该期货的历史价格，向broker请求后返回
        // 另一条在这里加入订阅的键值对，一旦价格发生变动，broker消息推送过来后，会调用静态方法告知所有订阅该期货的用户
        // 价格的变化
        Long futureId = Long.parseLong(message.toString());
        subscribes.put(session, futureId);
    }

    public static void priceFloatingInform(Long futureId,  Float price) throws IOException {
        for(WebSocketSession session: sessions){
            if(subscribes.get(session).equals(futureId)){ // 如果该trader正在查看价格走势，则告知其价格变化
                TextMessage msg = new TextMessage(price.toString());
                session.sendMessage(msg);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug(webSocketSession.getId()+" connection closed..." + closeStatus.toString());
        // 关闭连接后，将该会话从订阅消息以及会话队列中删除
        sessions.remove(webSocketSession);
        subscribes.remove(webSocketSession);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);
        logger.log(Level.INFO, session.getId()+" Connection established");
    }

}
