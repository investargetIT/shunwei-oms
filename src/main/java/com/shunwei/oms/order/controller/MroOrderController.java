package com.shunwei.oms.order.controller;

import com.shunwei.oms.common.constants.ApiConstants;
import com.shunwei.oms.common.controller.BaseController;
import com.shunwei.oms.common.dto.ApiResult;
import com.shunwei.oms.common.dto.IdsRequest;
import com.shunwei.oms.order.dto.MroOrderRequestDTO;
import com.shunwei.oms.order.dto.MroOrderResponseDTO;
import com.shunwei.oms.order.service.MroOrderService;
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

@Tag(name = "MRO 订单管理", description = "APIs for managing MRO orders")
@RestController
@RequestMapping("/mro-orders")
public class MroOrderController extends BaseController {

    @Autowired
    private MroOrderService mroOrderService;

    // 新增 MRO 订单的成功消息
    public static final String MESSAGE_SUCCESS_MRO_ORDERS_CREATED = "MRO order created successfully.";
    public static final String MESSAGE_SUCCESS_MRO_ORDERS_UPDATED = "MRO order updated successfully.";
    public static final String MESSAGE_SUCCESS_MRO_ORDERS_DELETED = "MRO order deleted successfully.";
    public static final String MESSAGE_SUCCESS_MRO_ORDER_RETRIEVED = "Successfully retrieved MRO orders.";
    public static final String MESSAGE_SUCCESS_MRO_ORDERS_RETRIEVED = "Successfully retrieved all MRO orders.";
    public static final String MESSAGE_NO_MRO_ORDERS_WITH_ID = "No MRO order found with the given ID.";

    @Operation(summary = "Create a new MRO order", description = "Creates a new MRO order in the system")
    @PostMapping
    public ResponseEntity<ApiResult<MroOrderResponseDTO>> createMroOrder(@RequestBody MroOrderRequestDTO mroOrderRequestDTO) {
        return handleRequest(() -> {
            MroOrderResponseDTO createdOrder = mroOrderService.createMroOrder(mroOrderRequestDTO);
            return ApiResult.success(createdOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_MRO_ORDERS_CREATED);
        });
    }

    @Operation(summary = "Update an existing MRO order", description = "Updates an existing MRO order")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<MroOrderResponseDTO>> updateMroOrder(@PathVariable Long id, @RequestBody MroOrderRequestDTO mroOrderRequestDTO) {
        return handleRequest(() -> {
            MroOrderResponseDTO updatedOrder = mroOrderService.updateMroOrder(id, mroOrderRequestDTO);
            return ApiResult.success(updatedOrder, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_MRO_ORDERS_UPDATED);
        });
    }

    @Operation(summary = "Delete multiple MRO orders", description = "Removes multiple MRO orders from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteMroOrders(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty.");
            }
            mroOrderService.deleteMroOrders(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_MRO_ORDERS_DELETED);
        });
    }

    @Operation(summary = "Get MRO order by ID", description = "Retrieve a specific MRO order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<MroOrderResponseDTO>> getMroOrderById(@PathVariable Long id) {
        return handleRequest(() -> {
            MroOrderResponseDTO mroOrderDTO = mroOrderService.getMroOrderById(id);
            return ApiResult.success(mroOrderDTO, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_MRO_ORDERS_RETRIEVED);
        });
    }

    @Operation(summary = "Search MRO orders", description = "Search MRO orders with filters")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<MroOrderResponseDTO>>> searchMroOrders(@RequestParam Map<String, String> searchParams,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return handleRequest(() -> {
            Page<MroOrderResponseDTO> orders = mroOrderService.searchMroOrders(searchParams, pageable);
            return ApiResult.success(orders, ApiConstants.CODE_BUSINESS_SUCCESS, MESSAGE_SUCCESS_MRO_ORDERS_RETRIEVED);
        });
    }
}
