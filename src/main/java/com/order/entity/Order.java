package com.order.entity;

import javax.persistence.*;

@Entity
@Table(name="order")
public class Order {

    //主键,也作为订单号使用
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //用户
    @Column(name = "userid")
    private String userid;

    //商品id列表，英文逗号分隔
    @Column(name = "productids")
    private String productids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductids() {
        return productids;
    }

    public void setProductids(String productids) {
        this.productids = productids;
    }

}
