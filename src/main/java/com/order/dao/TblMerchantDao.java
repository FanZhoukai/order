package com.order.dao;

import com.order.entity.TblMerchant;
import com.order.entity.TblOrderConsumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface TblMerchantDao extends CrudRepository<TblMerchant, Integer> { }
