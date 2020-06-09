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
        Optional<Product> opt =productDao.findById(Integer.parseInt(id));
        return opt.orElseGet(() -> new Product());
    }

    @Override
    public String setProduct(String productInfo) {
        JSONObject jsonObject = JSONObject.parseObject(productInfo);
        Map<String, Object> jsonDataMap = (Map<String, Object>) JSONObject.parseObject(jsonObject.toJSONString());
        Product product = new Product();
        product.setName((String) jsonDataMap.get("name"));
        product.setNumber(Integer.parseInt((String) jsonDataMap.get("number")));
        product = productDao.save(product);
        System.out.println(product.getId());
        return String.valueOf(product.getId());
    }

    @Override
    public boolean doPurchase(String productId) {
        boolean flag = false;
        return flag;
    }

}
