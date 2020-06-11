package com.order.entity;


import javax.persistence.*;

@Entity
@Table(name="tbl_consumer")
public class TblConsumer {

    //消费者id
    @Id
    @Column(name = "consumerid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumerid;

    //消费者持有金额
    @Column(name = "consumermoney")
    private long consumermoney;

    public int getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(int consumerid) {
        this.consumerid = consumerid;
    }

    public long getConsumermoney() {
        return consumermoney;
    }

    public void setConsumermoney(long consumermoney) {
        this.consumermoney = consumermoney;
    }
}
