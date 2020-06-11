package com.order.entity;

import com.alibaba.fastjson.JSONArray;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name="tbl_order_consumer")
public class TblOrderConsumer {

    public TblOrderConsumer(Map<String, Object> jsonDataMap) {
        /*
         * {"sumproductjson":[{"productid":"","productprice":"","productnum":"","merchantid":""}],"sumprise":"","consumerid":""}
         * */
        setSumproductjson((JSONArray) jsonDataMap.get("sumproductjson"));
        setSumprise(Long.parseLong((String) jsonDataMap.get("sumprise")));
        setConsumerid(Integer.parseInt((String) jsonDataMap.get("consumerid")));
    }

    //主键，消费者订单号
    @Id
    @Column(name = "consumerorderid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumerorderid;

    //商品列表，含商品id+商品单价+商品数目+商品所属商户id
    @Column(name = "sumproductjson")
    private JSONArray sumproductjson;

    //订单总金额
    @Column(name = "sumprise")
    private long sumprise;

    //下单用户id
    @Column(name = "consumerid")
    private int consumerid;

    public int getConsumerorderid() {
        return consumerorderid;
    }

    public void setConsumerorderid(int consumerorderid) {
        this.consumerorderid = consumerorderid;
    }

    public JSONArray getSumproductjson() {
        return sumproductjson;
    }

    public void setSumproductjson(JSONArray sumproductjson) {
        this.sumproductjson = sumproductjson;
    }

    public long getSumprise() {
        return sumprise;
    }

    public void setSumprise(long sumprise) {
        this.sumprise = sumprise;
    }

    public int getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(int consumerid) {
        this.consumerid = consumerid;
    }
}
