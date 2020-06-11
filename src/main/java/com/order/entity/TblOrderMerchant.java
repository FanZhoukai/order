package com.order.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="tbl_order_merchant")
public class TblOrderMerchant {

    //主键，商户订单号
    @Id
    @Column(name = "merchantorderid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int merchantorderid;

    //所属的消费者订单号
    @Column(name = "consumerorderid")
    private int consumerorderid;

    //商户所负责的这部分商品列表，含商品id+商品单价+商品数目+商品所属商户id
    @Column(name = "partproductjson")
    private String partproductjson;


    //商户所负责的这部分商品，总金额
    @Column(name = "partprise")
    private long partprise;

    //所属商户id
    @Column(name = "merchantid")
    private int merchantid;


    public int getMerchantorderid() {
        return merchantorderid;
    }

    public void setMerchantorderid(int merchantorderid) {
        this.merchantorderid = merchantorderid;
    }

    public int getConsumerorderid() {
        return consumerorderid;
    }

    public void setConsumerorderid(int consumerorderid) {
        this.consumerorderid = consumerorderid;
    }

    public String getPartproductjson() {
        return partproductjson;
    }

    public void setPartproductjson(String partproductjson) {
        if (null != this.partproductjson && null != partproductjson) {
            this.partproductjson = this.partproductjson + "$$$" + partproductjson;
        } else {
            this.partproductjson = partproductjson;
        }
    }

    public long getPartprise() {
        return partprise;
    }

    public void setPartprise(long partprise) {
        this.partprise = this.partprise + partprise;
    }

    public int getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(int merchantid) {
        this.merchantid = merchantid;
    }

}
