package com.order.order.service.impl;

import com.order.order.dao.ProductDao;
import com.order.order.entity.Product;
import com.order.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProduct(String id) {
        return productDao.findById(Integer.parseInt(id));
    }

    @Override
    public boolean setProduct(String productInfo) {
        boolean flag = false;
        return flag;
    }

    @Override
    public boolean doPurchase(String productId) {
        boolean flag = false;
        return flag;
    }

}
