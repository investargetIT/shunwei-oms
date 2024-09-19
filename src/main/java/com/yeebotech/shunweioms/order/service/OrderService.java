package com.yeebotech.shunweioms.order.service;

import com.yeebotech.shunweioms.customer.dto.CustomerDTO;
import com.yeebotech.shunweioms.order.dto.OrderRequestDTO;
import com.yeebotech.shunweioms.order.dto.OrderResponseDTO;
import com.yeebotech.shunweioms.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    void deleteOrders(List<Long> ids);

    OrderResponseDTO getOrderById(Long id);

    Page<OrderResponseDTO> searchOrders(Map<String, String> searchParams, Pageable pageable);
}
