package com.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.order.dao.OrderDao;
import com.order.dao.ProductDao;
import com.order.entity.Order;
import com.order.entity.Product;
import com.order.service.OrderService;
import com.order.util.common.CommonUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Product getProduct(String id) {
        Optional<Product> opt =productDao.findById(Integer.parseInt(id));
        return opt.orElseGet(() -> new Product());
    }

    @Override
    public String setProduct(String productInfo) {
        /*
        * {"name":"pro2","number":"3"}
        * */
        Map<String, Object> jsonDataMap = CommonUtil.parseJson(productInfo);
        Product product = new Product();
        product.setName((String) jsonDataMap.get("name"));
        product.setNumber(Integer.parseInt((String) jsonDataMap.get("number")));
        product = productDao.save(product);
        System.out.println(product.getId());
        return String.valueOf(product.getId());
    }



    @Override
    @Transactional
    public synchronized String doPurchase(String purchaseJson) {
        /*
        * {"userid":"","productids":"1,2,3"}
         * */
        Map<String, Object> jsonDataMap = CommonUtil.parseJson(purchaseJson);
        String[] productids = ((String) jsonDataMap.get("productids")).split(",");
        List<Product> listProduct = new ArrayList<Product>();
        //检查库存是否存在
        for (String productid: productids) {
            Product product = getProduct(productid);
            if (product.getNumber() == 0) {
                return "下单失败，无库存，productid=" + productid;
            }
        }
        //减库存
        for (Product product: listProduct) {
            product.setNumber(product.getNumber() - 1);
            productDao.save(product);
        }
        //生成订单
        Order order = new Order();
        order.setUserid((String) jsonDataMap.get("userid"));
        order.setProductids((String) jsonDataMap.get("productids"));
        System.out.println(order.getId());
        System.out.println(order.getUserid());
        System.out.println(order.getProductids());
        orderDao.save(order);
        return "成功";
    }



}
















