package com.order.service;

import com.order.entity.TblProduct;


public interface OrderService {

    public TblProduct getProduct(String id);

    public String setProduct(String productJson);

    public String doPurchase(String purchaseJson);

}
