package com.yeebotech.shunweioms.order.service;

import com.yeebotech.shunweioms.order.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrders(List<Long> ids);

    OrderDTO getOrderById(Long id);

    Page<OrderDTO> searchOrders(Map<String, String> searchParams, Pageable pageable);
}
