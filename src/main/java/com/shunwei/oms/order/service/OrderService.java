package com.shunwei.oms.order.service;

import com.shunwei.oms.order.dto.OrderRequestDTO;
import com.shunwei.oms.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    void deleteOrders(List<Long> ids);

    OrderResponseDTO getOrderById(Long id);

    Page<OrderResponseDTO> searchOrders(Map<String, String> searchParams, Pageable pageable);
}
