package com.yeebotech.shunweioms.order.controller;

import com.yeebotech.shunweioms.common.constants.ApiConstants;
import com.yeebotech.shunweioms.common.controller.BaseController;
import com.yeebotech.shunweioms.common.dto.ApiResult;
import com.yeebotech.shunweioms.order.dto.OrderDTO;
import com.yeebotech.shunweioms.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "订单管理", description = "APIs for managing orders")
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    // 新增订单的成功消息
    public static final String MESSAGE_SUCCESS_ORDER_CREATED = "Order created successfully.";
    public static final String MESSAGE_SUCCESS_ORDER_UPDATED = "Order updated successfully.";
    public static final String MESSAGE_SUCCESS_ORDER_DELETED = "Order deleted successfully.";
    public static final String MESSAGE_SUCCESS_ORDER_RETRIEVED = "Successfully retrieved order.";
    public static final String MESSAGE_SUCCESS_ORDERS_RETRIEVED = "Successfully retrieved all orders.";

    @Operation(summary = "Create a new order", description = "Creates a new order in the system")
    @PostMapping
    public ResponseEntity<ApiResult<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        return handleRequest(() -> {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ApiResult.success(createdOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_CREATED);
        });
    }

    @Operation(summary = "Update an existing order", description = "Updates an existing order")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<OrderDTO>> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return handleRequest(() -> {
            OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
            return ApiResult.success(updatedOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_UPDATED);
        });
    }

    @Operation(summary = "Delete multiple orders", description = "Removes multiple orders from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteOrders(@RequestBody List<Long> ids) {
        return handleRequest(() -> {
            orderService.deleteOrders(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_DELETED);
        });
    }

    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<OrderDTO>> getOrderById(@PathVariable Long id) {
        return handleRequest(() -> {
            OrderDTO orderDTO = orderService.getOrderById(id);
            return ApiResult.success(orderDTO, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_RETRIEVED);
        });
    }

    @Operation(summary = "Search orders", description = "Search orders with filters")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<OrderDTO>>> searchOrders(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        return handleRequest(() -> {
            Page<OrderDTO> orders = orderService.searchOrders(searchParams, pageable);
            return ApiResult.success(orders, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDERS_RETRIEVED);
        });
    }
}
