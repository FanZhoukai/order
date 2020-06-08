package com.order.order.controller;

import com.order.order.entity.Product;
import com.order.order.service.OrderService;
import com.order.order.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OrderApplication {

    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @RequestMapping("/getProduct")
    public String getProduct(@RequestParam(value = "id") String id) {
        Product product = orderService.getProduct(id);
        return product.getName();
    }

    @RequestMapping("/setProduct")
    public String setProduct(@RequestParam(value = "product") String product) {

        return "set successfull";
    }
}
