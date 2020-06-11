package com.order.entity;

import javax.persistence.*;

@Entity
@Table(name="tbl_merchant")
public class TblMerchant {

    //商户id
    @Id
    @Column(name = "merchantid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int merchantid;

    public int getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(int merchantid) {
        this.merchantid = merchantid;
    }
}
