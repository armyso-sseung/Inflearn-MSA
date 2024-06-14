package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrdersController {
    private final Environment env;

    private final OrderService orderService;

    /**
     * @title   : 상태체크
     * @author  : 정승현
     * @since   : 2024/06/14
     * @return  : 서버 포트 메시지
     */
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    /**
     * @title   : 주문 등록
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 사용자 아이디 및 주문 정보
     * @return  : 주문 정보
     */
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderInfo) {
        orderInfo.setUserId(userId);
        ResponseOrder order = orderService.createOrder(orderInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * @title   : 주문 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 사용자 아이디
     * @return  : 주문 목록
     */
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrderList(@PathVariable("userId") String userId) {
        List<ResponseOrder> orderList = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(orderList);
    }

    /**
     * @title   : 주문 조회 ( 조건 : 주문 번호 )
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : 주문 번호
     * @return  : 주문 정보
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrder(@PathVariable("orderId") String orderId) {
        ResponseOrder order = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok().body(order);
    }
}
