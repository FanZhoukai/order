package com.order.entity;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name="product")
public class Product {

    //主键
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //商品名称
    @Column(name = "name")
    private String name;

    //商品库存
    @Column(name = "number")
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
