package com.cts.controller;

import com.cts.entity.Order;
import com.cts.fix.FixInitiator;
import com.cts.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2017/6/2.
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    private FixInitiator initiator;

    @RequestMapping(value = "/order/", method = RequestMethod.POST)
    public ResponseEntity<Void> newOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
        // send the order to the broker and get result id
        HttpHeaders headers = new HttpHeaders();
        Long orderId = 0l;
        orderService.sendOrder(order, "BrokerGateway1");
        headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(orderId).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
//    @RequiresRoles(value = {"trader"})
    public ResponseEntity<Order> cancelOrder(@PathVariable("id") Long id) {
        System.out.println("Cancel Order with id " + id);
        // send the cancel request to the broker gateway
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresAuthentication
//    @RequiresRoles(value = {"trader"})
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        System.out.println("Fetching Order with id " + id);
        // retrieve the order from the broker gateway
        Order order = new Order();
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> listAllUsers() {
        List<Order> orders = new ArrayList<Order>();
        // get all the orders from broker gateway according to the firm id
        if(orders.isEmpty()){
            return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
}
