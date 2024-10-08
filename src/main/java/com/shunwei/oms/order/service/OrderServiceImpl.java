package com.shunwei.oms.order.service;

import com.shunwei.oms.customer.dto.CustomerDTO;
import com.shunwei.oms.customer.entity.Customer;
import com.shunwei.oms.customer.repository.CustomerRepository;
import com.shunwei.oms.goods.repository.GoodsRepository;
import com.shunwei.oms.goods.service.GoodsService;
import com.shunwei.oms.goods.dto.GoodsDTO;
import com.shunwei.oms.goods.entity.Goods;
import com.shunwei.oms.order.dto.OrderRequestDTO;
import com.shunwei.oms.order.dto.OrderResponseDTO;
import com.shunwei.oms.order.entity.Order;
import com.shunwei.oms.order.repository.OrderRepository;
import com.shunwei.oms.order.service.specification.OrderSpecification;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GoodsService goodsService;
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderRequestDTO, order);
        // 手动处理 goods_id，确保 Goods 实体被正确设置
        Long goodsId = orderRequestDTO.getGoodsId();
        if (goodsId != null) {
            Optional<Goods> goodsOpt = goodsRepository.findById(goodsId);
            if (goodsOpt.isPresent()) {
                order.setGoods(goodsOpt.get());
            } else {
                throw new RuntimeException("Goods not found with id: " + goodsId);
            }
        }

        // 手动处理 customer_id，确保 Customer 实体被正确设置
        Long customerId = orderRequestDTO.getCustomerId();
        if (customerId != null) {
            Optional<Customer> customerOpt = customerRepository.findById(customerId);
            if (customerOpt.isPresent()) {
                order.setCustomer(customerOpt.get());
            } else {
                throw new RuntimeException("Customer not found with id: " + customerId);
            }
        }
        // 保存到数据库
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            // 处理 goods_id，确保 Goods 实体被正确设置
            Long goodsId = orderRequestDTO.getGoodsId();
            if (goodsId != null) {
                Optional<Goods> goodsOpt = goodsRepository.findById(goodsId);
                if (goodsOpt.isPresent()) {
                    order.setGoods(goodsOpt.get());
                } else {
                    throw new RuntimeException("Goods not found with id: " + goodsId);
                }
            }

            // 手动处理 customer_id，确保 Customer 实体被正确设置
            Long customerId = orderRequestDTO.getCustomerId();
            if (customerId != null) {
                Optional<Customer> customerOpt = customerRepository.findById(customerId);
                if (customerOpt.isPresent()) {
                    order.setCustomer(customerOpt.get());
                } else {
                    throw new RuntimeException("Customer not found with id: " + customerId);
                }
            }
            BeanUtils.copyProperties(orderRequestDTO, order, "id", "createdAt", "updatedAt");
            order.setUpdatedAt(LocalDateTime.now());
            Order updatedOrder = orderRepository.save(order);
            return convertToDTO(updatedOrder);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteOrders(List<Long> ids) {
        orderRepository.deleteByIds(ids);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return convertToDTO(order.get());
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Override
    public Page<OrderResponseDTO> searchOrders(Map<String, String> searchParams, Pageable pageable) {
        // Create the Specification based on searchParams
        Specification<Order> spec = OrderSpecification.bySearchParams(searchParams);

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );
        // Execute the query and return paginated results
        Page<Order> orders = orderRepository.findAll(spec, sortedPageable);

        // Convert entities to DTOs
        return orders.map(this::convertToDTO);
    }

    private OrderResponseDTO convertToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        Goods goods = order.getGoods();
        BeanUtils.copyProperties(order, dto);
        GoodsDTO goodsDTO = goodsService.goodsToDTO(goods);
        dto.setGoods(goodsDTO);

        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = order.getCustomer();
        BeanUtils.copyProperties(customer, customerDTO);
        dto.setCustomer(customerDTO);
        return dto;
    }
}
