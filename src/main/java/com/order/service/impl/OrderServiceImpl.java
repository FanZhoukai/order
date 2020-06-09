package com.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.order.dao.ProductDao;
import com.order.entity.Product;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProduct(String id) {
        System.out.println("productDao:" + productDao);
        Optional<Product> opt =productDao.findById(Integer.parseInt(id));

        return Optional.ofNullable(opt).map(product -> product.getName()).get();
    }

    @Override
    public String setProduct(String productInfo) {
        String returnMsg = "success ";
        JSONObject jsonObject = JSONObject.parseObject(productInfo);
        Map<String, Object> jsonDataMap = (Map<String, Object>) JSONObject.parseObject(jsonObject.toJSONString());
        Product product = new Product();
        product.setName((String) jsonDataMap.get("name"));
        product.setNumber(Integer.parseInt((String) jsonDataMap.get("number")));
        product = productDao.save(product);
        return returnMsg;
    }

    @Override
    public boolean doPurchase(String productId) {
        boolean flag = false;
        return flag;
    }

}
