package com.order.service;

import com.order.entity.Product;

import java.util.Optional;


public interface OrderService {

    public Product getProduct(String id);

    public String setProduct(String productJson);

    public String doPurchase(String purchaseJson);

}
