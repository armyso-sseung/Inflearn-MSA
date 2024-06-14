package com.example.orderservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    /**
     * @title   : 주문 조회 ( 조건 : 주문 번호 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    OrderEntity findByOrderId(String orderId);

    /**
     * @title   : 주문 조회 ( 조건 : 사용자 아이디 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    Iterable<OrderEntity> findByUserId(String userId);
}
