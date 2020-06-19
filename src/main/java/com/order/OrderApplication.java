package com.order;

import com.order.entity.TblProduct;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@SpringBootApplication
@RestController
public class OrderApplication {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}");
        SpringApplication.run(OrderApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("我是log4j，我被成功的引入进来了，我是info");
        logger.debug("我是log4j，我被成功的引入进来了，我是debugger");
        logger.warn("我是log4j，我被成功的引入进来了，我是warn");
        return String.format("Hello %s!", name);
    }

    @RequestMapping("/getProduct")
    public String getProduct(@RequestParam(value = "id") String id) {
        TblProduct product = orderService.getProduct(id);
        return String.valueOf(product.getProductprice());
    }

    @RequestMapping("/setProduct")
    public String setProduct(@RequestParam(value = "productJson") String productJson) {
        String id = orderService.setProduct(productJson);
        return id;
    }

    @RequestMapping("/doPurchase")
    public String doPurchase(@RequestParam(value = "purchaseJson") String purchaseJson) {
        String msg = orderService.doPurchase(purchaseJson);
        return msg;
    }
}
