package com.shunwei.oms.order.controller;

import com.shunwei.oms.common.constants.ApiConstants;
import com.shunwei.oms.common.controller.BaseController;
import com.shunwei.oms.common.dto.ApiResult;
import com.shunwei.oms.common.dto.IdsRequest;
import com.shunwei.oms.order.dto.OrderRequestDTO;
import com.shunwei.oms.order.dto.OrderResponseDTO;
import com.shunwei.oms.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public static final String MESSAGE_NO_ORDER_WITH_ID = "No order found with the given ID.";

    @Operation(summary = "Create a new order", description = "Creates a new order in the system")
    @PostMapping
    public ResponseEntity<ApiResult<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return handleRequest(() -> {
            OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
            return ApiResult.success(createdOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_CREATED);
        });
    }

    @Operation(summary = "Update an existing order", description = "Updates an existing order")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<OrderResponseDTO>> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
        return handleRequest(() -> {
            OrderResponseDTO updatedOrder = orderService.updateOrder(id, orderRequestDTO);
            return ApiResult.success(updatedOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_UPDATED);
        });
    }

    @Operation(summary = "Delete multiple orders", description = "Removes multiple orders from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteOrders(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty.");
            }
            orderService.deleteOrders(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_DELETED);
        });
    }

    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<OrderResponseDTO>> getOrderById(@PathVariable Long id) {
        return handleRequest(() -> {
            OrderResponseDTO orderDTO = orderService.getOrderById(id);
            return ApiResult.success(orderDTO, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDER_RETRIEVED);
        });
    }

    @Operation(summary = "Search orders", description = "Search orders with filters")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<OrderResponseDTO>>> searchOrders(@RequestParam Map<String, String> searchParams,  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return handleRequest(() -> {
            Page<OrderResponseDTO> orders = orderService.searchOrders(searchParams, pageable);
            return ApiResult.success(orders, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_ORDERS_RETRIEVED);
        });
    }
}
