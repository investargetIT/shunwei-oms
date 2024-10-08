package com.shunwei.oms.order.service;

import com.shunwei.oms.order.dto.MroOrderResponseDTO;
import com.shunwei.oms.order.dto.MroOrderRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MroOrderService {

    MroOrderResponseDTO createMroOrder(MroOrderRequestDTO orderRequestDTO);

    MroOrderResponseDTO updateMroOrder(Long id, MroOrderRequestDTO orderRequestDTO);

    void deleteMroOrders(List<Long> ids);

    MroOrderResponseDTO getMroOrderById(Long id);

    Page<MroOrderResponseDTO> searchMroOrders(Map<String, String> searchParams, Pageable pageable);
}
