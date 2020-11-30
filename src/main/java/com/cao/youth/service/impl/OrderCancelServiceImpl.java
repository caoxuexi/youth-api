package com.cao.youth.service.impl;

import com.cao.youth.bo.OrderMessageBO;
import com.cao.youth.exception.http.ServerErrorException;
import com.cao.youth.model.Order;
import com.cao.youth.repository.OrderRepository;
import com.cao.youth.repository.SkuRepository;
import com.cao.youth.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 曹学习
 * @description OrderCancelServiceImpl
 * @date 2020/9/16 11:02
 */
@Service
public class OrderCancelServiceImpl implements OrderCancelService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    @Override
    public void cancel(OrderMessageBO messageBO) {
        if(messageBO.getOrderId()<=0){
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getOrderId());
    }


    private void cancel(Long oid){
        Optional<Order> orderOptional=orderRepository.findById(oid);
        Order order=orderOptional.orElseThrow(()->{
            throw new ServerErrorException(9999);
        });
        int res= orderRepository.cancelOrder(oid);
        if(res!=1){
            return;
        }
        order.getSnapItems().forEach(i->{
            skuRepository.recoverStock(i.getId(),i.getCount().longValue());
        });
    }
}
