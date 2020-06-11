package com.order.dao;

import com.order.entity.TblOrderConsumer;
import com.order.entity.TblOrderMerchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface TblOrderMerchantDao extends CrudRepository<TblOrderMerchant, Integer> { }
