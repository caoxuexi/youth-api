package com.cao.youth.service;

import com.cao.youth.dto.OrderDTO;
import com.cao.youth.logic.OrderChecker;
import com.cao.youth.model.Order;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author 曹学习
 * @description OrderService
 * @date 2020/8/31 12:16
 */
public interface OrderService {
    OrderChecker isOk(Long uid, OrderDTO orderDTO);
    Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker);
    Page<Order> getUnpaid(Integer page, Integer size);
    Page<Order> getByStatus(Integer status,Integer page,Integer size);

    Optional<Order> getOrderDetail(Long oid);
    void updateOrderPrepayId(Long orderId,String prePayId);
}
