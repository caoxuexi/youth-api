package com.cao.youth.repository;

import com.cao.youth.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author 曹学习
 * @description OrderRepository
 * @date 2020/9/3 23:25
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, Integer status, Long uid, Pageable pageable);
    Page<Order> findByUserId(Long uid,Pageable pageable);
    Page<Order> findByUserIdAndStatus(Long uid,Integer status,Pageable pageable);
    Optional<Order> findFirstByUserIdAndId(Long uid, Long oid);
    Optional<Order> findFirstByOrderNo(String orderNo);
    @Query("select o.orderNo from Order o where o.id=:oid")
    Optional<String> findOrderNoById(Long oid);

    @Modifying
    @Query("update Order o set o.status=:status where o.orderNo=:orderNo")
    int updateStatusByOrderNo(String orderNo, Integer status);

    @Modifying
    @Query("update Order o set o.status=5 where o.status = 1 and o.id=:oid")
    int cancelOrder(Long oid);
}
