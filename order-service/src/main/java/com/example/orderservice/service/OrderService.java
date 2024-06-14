package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;

import java.util.List;

public interface OrderService {
    /**
     * @title   : 주문 등록
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    ResponseOrder createOrder(RequestOrder orderInfo);

    /**
     * @title   : 주문 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    List<ResponseOrder> getOrdersByUserId(String userId);

    /**
     * @title   : 주문 조회 ( 조건 : 주문 번호 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    ResponseOrder getOrderByOrderId(String orderId);
}
