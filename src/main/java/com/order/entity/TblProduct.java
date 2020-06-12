package com.order.entity;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name="tbl_product")
public class TblProduct {

    public TblProduct() { }

    public TblProduct(Map<String, Object> jsonDataMap) {
        /*
         * {"productnum":"","productprice":"","merchantid":""}
         * */
        setProductnum(Integer.parseInt((String) jsonDataMap.get("productnum")));
        setProductprice(Long.parseLong((String) jsonDataMap.get("productprice")));
        setMerchantid(Integer.parseInt((String) jsonDataMap.get("merchantid")));
    }
    //主键，商品id
    @Id
    @Column(name = "productid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productid;

    //商品库存
    @Column(name = "productnum")
    private int productnum;

    //商品价格
    @Column(name = "productprice")
    private long productprice;

    //所属商家id
    @Column(name = "merchantid")
    private int merchantid;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getProductnum() {
        return productnum;
    }

    public void setProductnum(int productnum) {
        this.productnum = productnum;
    }

    public long getProductprice() {
        return productprice;
    }

    public void setProductprice(long productprice) {
        this.productprice = productprice;
    }

    public int getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(int merchantid) {
        this.merchantid = merchantid;
    }
}
