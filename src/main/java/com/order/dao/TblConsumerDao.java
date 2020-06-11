package com.order.dao;

import com.order.entity.TblConsumer;
import com.order.entity.TblOrderConsumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface TblConsumerDao extends CrudRepository<TblConsumer, Integer> { }
