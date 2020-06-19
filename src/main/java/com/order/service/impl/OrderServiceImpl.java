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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TblProductDao tblproductDao;
    @Autowired
    private TblOrderConsumerDao tblOrderConsumerDao;
    @Autowired
    private TblOrderMerchantDao tblOrderMerchantDao;
    @Autowired
    private TblConsumerDao tblConsumerDao;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public TblProduct getProduct(String id) {
        Optional<TblProduct> opt =tblproductDao.findById(Integer.parseInt(id));
        return opt.orElseGet(() -> new TblProduct());
    }

    @Override
    public String setProduct(String productInfo) {
        /*
        * {"productnum":"","productprice":"","merchantid":""}
        * */
        Map<String, Object> productInfoMap = CommonUtil.parseJson(productInfo);
        TblProduct product = new TblProduct(productInfoMap);
        product = tblproductDao.save(product);
        logger.debug("保存商品：" + productInfo);
        return String.valueOf(product.getProductid());
    }



    @Override
    @Transactional
    public synchronized String doPurchase(String purchaseJson) {
        /*
        * {"sumproductjson":[{"productid":"","productprice":"","productnum":"","merchantid":""}],"sumprise":"","consumerid":""}
         * */
        Map<String, Object> purchaseJsonMap = CommonUtil.parseJson(purchaseJson);

        //检查金额是否足够。默认这样一个逻辑：如果金额大于零说明已付款，此时不会出现改价操作。如果金额=0，则可以改价。
        Optional<TblConsumer> consumerOptional = tblConsumerDao.findById(Integer.parseInt((String) purchaseJsonMap.get("consumerid")));
        TblConsumer tblConsumer = consumerOptional.orElseGet(() -> new TblConsumer());
        long consumermoney = tblConsumer.getConsumermoney();
        long ordersummoney = Long.parseLong((String) purchaseJsonMap.get("sumprise"));
        if (ordersummoney > consumermoney) {
            logger.debug("金额不足，不可创建订单");
            return "金额不足，不可创建订单";
        }

        //检查库存是否足够
        JSONArray sumproductjsonArray = (JSONArray) purchaseJsonMap.get("sumproductjson");
        Map<TblProduct, Integer> decrProNumMap = new HashMap<TblProduct, Integer>();
        for(int i=0;i<sumproductjsonArray.size();i++) {
            Optional<TblProduct> tblProductOptional = tblproductDao.findById(Integer.parseInt((String) sumproductjsonArray.getJSONObject(i).get("productid")));
            TblProduct tblProduct = tblProductOptional.orElseGet(() -> new TblProduct());
            int orderNeedNum = Integer.parseInt((String) sumproductjsonArray.getJSONObject(i).get("productnum"));
            if (tblProduct.getProductnum() >= orderNeedNum) {
                decrProNumMap.put(tblProduct, orderNeedNum);
            }else {
                logger.debug("库存不够，订单创建失败");
                return "库存不够，订单创建失败";
            }
        }

        //扣钱
        tblConsumer.setConsumermoney(tblConsumer.getConsumermoney() - ordersummoney);
        tblConsumerDao.save(tblConsumer);
        logger.debug("已扣钱");

        //减库存
        for (TblProduct tblProduct: decrProNumMap.keySet()) {
            tblProduct.setProductnum(tblProduct.getProductnum() - decrProNumMap.get(tblProduct));
            tblproductDao.save(tblProduct);
            logger.debug("减库存");
        }

        //生成用户视角的总订单
        TblOrderConsumer tblOrderConsumer = new TblOrderConsumer(purchaseJsonMap);
        tblOrderConsumer = tblOrderConsumerDao.save(tblOrderConsumer);
        logger.debug("生成用户视角的总订单");

        //生成商家视角的订单
        Set<String> merchantidSet = new HashSet<String>();
        Map<String, TblOrderMerchant> tblOrderMerchantMap = new HashMap<String, TblOrderMerchant>();
        sumproductjsonArray = tblOrderConsumer.getSumproductjsonArray();
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
            logger.debug("生成商家视角的订单");
        }
        return "成功";
    }



}
















