package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Environment env;

    private final OrderRepository orderRepository;

    /**
     * @title       : 주문 등록
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 사용자 아이디 및 주문 정보를 이용해 주문을 등록 한다.
     */
    @Override
    public ResponseOrder createOrder(RequestOrder orderInfo) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderInfo, OrderDto.class);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        return mapper.map(orderEntity, ResponseOrder.class);
    }

    /**
     * @title       : 주문 목록 조회
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 주문 목록을 조회 한다.
     */
    @Override
    public List<ResponseOrder> getOrdersByUserId(String userId) {
        Iterable<OrderEntity> dataList = orderRepository.findByUserId(userId);

        ModelMapper mapper = new ModelMapper();
        List<ResponseOrder> orderList = StreamSupport.stream(dataList.spliterator(), false)
                .map(order -> mapper.map(order, ResponseOrder.class))
                .collect(Collectors.toList());

        return orderList;
    }

    /**
     * @title       : 주문 조회 ( 조건 : 주문 번호 )
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 주문 번호를 통해서 주문 정보를 조회 한다.
     */
    @Override
    public ResponseOrder getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return new ModelMapper().map(orderEntity, ResponseOrder.class);
    }
}
