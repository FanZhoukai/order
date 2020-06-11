package com.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.order.dao.TblConsumerDao;
import com.order.dao.TblOrderConsumerDao;
import com.order.dao.TblOrderMerchantDao;
import com.order.dao.TblProductDao;
import com.order.entity.TblConsumer;
import com.order.entity.TblOrderConsumer;
import com.order.entity.TblOrderMerchant;
import com.order.entity.TblProduct;
import com.order.service.OrderService;
import com.order.util.common.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TblProductDao productDao;
    @Autowired
    private TblOrderConsumerDao tblOrderConsumerDao;
    @Autowired
    private TblOrderMerchantDao tblOrderMerchantDao;
    @Autowired
    private TblConsumerDao tblConsumerDao;

    @Override
    public TblProduct getProduct(String id) {
        Optional<TblProduct> opt =productDao.findById(Integer.parseInt(id));
        return opt.orElseGet(() -> new TblProduct());
    }

    @Override
    public String setProduct(String productInfo) {
        /*
        * {"productnum":"","productprice":"","merchantid":""}
        * */
        Map<String, Object> productInfoMap = CommonUtil.parseJson(productInfo);
        TblProduct product = new TblProduct(productInfoMap);
        product = productDao.save(product);
        return String.valueOf(product.getProductid());
    }



    @Override
    @Transactional
    public synchronized String doPurchase(String purchaseJson) {
        /*
        * {"sumproductjson":[{"productid":"","productprice":"","productnum":"","merchantid":""}],"sumprise":"","consumerid":""}
         * */
        Map<String, Object> purchaseJsonMap = CommonUtil.parseJson(purchaseJson);

        //检查库存是否存在
        for (String productid: productids) {
            TblProduct product = getProduct(productid);
            if (product.getNumber() == 0) {
                return "下单失败，无库存，productid=" + productid;
            }
        }
        //检查金额是否足够。默认这样一个逻辑：如果金额大于零说明已付款，此时不会出现改价操作。如果金额=0，则可以改价。
        Optional<TblConsumer> consumer = tblConsumerDao.findById(Integer.parseInt((String) purchaseJsonMap.get("consumerid")));
        long consumermoney = consumer.orElseGet(() -> new TblConsumer()).getConsumermoney();
        long ordersummoney = Long.parseLong((String) purchaseJsonMap.get("sumprise"));
        if (ordersummoney > consumermoney) {
            return "金额不足，不可创建订单";
        }
        //检查库存是否足够
        //TODO

        //减库存
        //TODO

        //生成用户视角的总订单
        TblOrderConsumer tblOrderConsumer = new TblOrderConsumer(purchaseJsonMap);
        tblOrderConsumer = tblOrderConsumerDao.save(tblOrderConsumer);

        //生成商家视角的订单
        Set<String> merchantidSet = new HashSet<String>();
        Map<String, TblOrderMerchant> tblOrderMerchantMap = new HashMap<String, TblOrderMerchant>();
        JSONArray sumproductjsonArray = tblOrderConsumer.getSumproductjson();
        for(int i=0;i<sumproductjsonArray.size();i++) {
            String merchantid = (String) sumproductjsonArray.getJSONObject(i).get("merchantid");
            if ( !merchantidSet.contains(merchantid) ) {
                TblOrderMerchant tblOrderMerchant = new TblOrderMerchant();
                tblOrderMerchant.setConsumerorderid(tblOrderConsumer.getConsumerorderid());
                tblOrderMerchant.setMerchantid(Integer.parseInt(merchantid));
                tblOrderMerchant.setPartprise(Long.parseLong((String) sumproductjsonArray.getJSONObject(i).get("productprice")));
                tblOrderMerchant.setPartproductjson(sumproductjsonArray.getJSONObject(i).toJSONString());
                tblOrderMerchantMap.put(merchantid, tblOrderMerchant);
            }else{
                TblOrderMerchant tblOrderMerchant = tblOrderMerchantMap.get(merchantid);
                tblOrderMerchant.setPartprise(Long.parseLong((String) sumproductjsonArray.getJSONObject(i).get("productprice")));
                tblOrderMerchant.setPartproductjson(sumproductjsonArray.getJSONObject(i).toJSONString());
            }
        }
        for (String merchantid: tblOrderMerchantMap.keySet()) {
            tblOrderMerchantDao.save(tblOrderMerchantMap.get(merchantid));
        }

        return "成功";
    }



}
















