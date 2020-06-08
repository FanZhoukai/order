package com.order.order.dao;

import com.order.order.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductDao {

    @Select("select count(1) from product")
    int findCount();


    @Select("select id, name from product where id = #{id}")
    Product findById(@Param("id")int id);
}
