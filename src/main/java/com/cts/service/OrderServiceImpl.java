package com.cts.service;

import com.cts.entity.Order;
import org.springframework.stereotype.Service;
import quickfix.*;


/**
 * Created by fy on 2017/5/31.
 */
@Service
public class OrderServiceImpl implements OrderService{
    public void sendOrder(Order order, String brokerId) {
        Message message = new Message();
        message.getHeader().setField(new StringField(8, "FIX.4.4"));
        message.getHeader().setField(new StringField(49, "TraderGateway"));
        message.getHeader().setField(new StringField(56, brokerId));
        message.getHeader().setField(new CharField(35, 'F'));
        message.setField(new IntField(16, order.getType()));
        message.setField(new StringField(11, order.getFuture().toString()));
        message.setField(new IntField(13, order.getQty()));
        message.setField(new IntField(12, 1));
        message.setField(new DoubleField(14, order.getPrice()));
        message.setField(new IntField(15, order.getSide()));
        message.setField(new StringField(17, "order id"));
        try {
            Session.sendToTarget(message);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }
}
