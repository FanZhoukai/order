package com.order.dao;

import com.order.entity.TblProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface TblProductDao extends CrudRepository<TblProduct, Integer> {}
