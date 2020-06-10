package com.order.dao;

import com.order.entity.Order;
import com.order.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface OrderDao extends CrudRepository<Order, Integer> {


}
