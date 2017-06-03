package com.cts.service;

import com.cts.entity.Order;

/**
 * Created by fy on 2017/6/3.
 */
public interface OrderService {
    void sendOrder(Order order, String brokerId);
}
