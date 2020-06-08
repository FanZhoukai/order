package com.order.order.service;

import com.order.order.entity.Product;
import org.springframework.stereotype.Service;


public interface OrderService {

    public Product getProduct(String id);

    public boolean setProduct(String productInfo);

    public boolean doPurchase(String productId);

}
