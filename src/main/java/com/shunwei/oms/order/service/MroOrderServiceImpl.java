package com.shunwei.oms.order.service;

import com.shunwei.oms.order.dto.MroOrderResponseDTO;
import com.shunwei.oms.order.entity.MroOrder;
import com.shunwei.oms.order.service.specification.MroOrderSpecification;
import com.shunwei.oms.order.dto.MroOrderRequestDTO;
import com.shunwei.oms.order.repository.MroOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MroOrderServiceImpl implements MroOrderService {

    @Autowired
    private MroOrderRepository mroOrderRepository;

    @Override
    public MroOrderResponseDTO createMroOrder(MroOrderRequestDTO mroOrderRequestDTO) {
        MroOrder mroOrder = new MroOrder();
        BeanUtils.copyProperties(mroOrderRequestDTO, mroOrder);
        // 保存到数据库
        MroOrder savedOrder = mroOrderRepository.save(mroOrder);
        return convertToDTO(savedOrder);
    }

    @Override
    public MroOrderResponseDTO updateMroOrder(Long id, MroOrderRequestDTO mroOrderRequestDTO) {
        Optional<MroOrder> optionalOrder = mroOrderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            MroOrder mroOrder = optionalOrder.get();
            BeanUtils.copyProperties(mroOrderRequestDTO, mroOrder, "id", "createdAt", "updatedAt");
            mroOrder.setUpdatedAt(LocalDateTime.now());
            MroOrder updatedOrder = mroOrderRepository.save(mroOrder);
            return convertToDTO(updatedOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public void deleteMroOrders(List<Long> ids) {
        mroOrderRepository.deleteByIds(ids);
    }

    @Override
    public MroOrderResponseDTO getMroOrderById(Long id) {
        Optional<MroOrder> mroOrder = mroOrderRepository.findById(id);
        if (mroOrder.isPresent()) {
            return convertToDTO(mroOrder.get());
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public Page<MroOrderResponseDTO> searchMroOrders(Map<String, String> searchParams, Pageable pageable) {
        // Create the Specification based on searchParams
        Specification<MroOrder> spec = MroOrderSpecification.bySearchParams(searchParams);

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );
        // Execute the query and return paginated results
        Page<MroOrder> orders = mroOrderRepository.findAll(spec, sortedPageable);

        // Convert entities to DTOs
        return orders.map(this::convertToDTO);
    }

    private MroOrderResponseDTO convertToDTO(MroOrder mroOrder) {
        MroOrderResponseDTO dto = new MroOrderResponseDTO();
        BeanUtils.copyProperties(mroOrder, dto);
        return dto;
    }
}
