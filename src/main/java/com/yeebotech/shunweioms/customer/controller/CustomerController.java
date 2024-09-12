package com.yeebotech.shunweioms.customer.controller;

import com.yeebotech.shunweioms.common.constants.ApiConstants;
import com.yeebotech.shunweioms.common.controller.BaseController;
import com.yeebotech.shunweioms.common.dto.ApiResult;
import com.yeebotech.shunweioms.common.dto.IdsRequest;
import com.yeebotech.shunweioms.customer.dto.CustomerDTO;
import com.yeebotech.shunweioms.customer.service.CustomerService;
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

@RestController
@RequestMapping("/customers")
@Tag(name = "客户管理", description = "APIs for customer management")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    // 创建客户
    @Operation(summary = "Create a new customer", description = "Creates a new customer in the system")
    @PostMapping
    public ResponseEntity<ApiResult<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return handleRequest(() -> {
            CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
            return ApiResult.success(createdCustomer, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CUSTOMER_CREATED);
        });
    }

    // 更新客户
    @Operation(summary = "Update an existing customer", description = "Updates the details of an existing customer")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CustomerDTO>> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return handleRequest(() -> {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
            return ApiResult.success(updatedCustomer, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CUSTOMER_UPDATED);
        });
    }

    // 批量删除客户
    @Operation(summary = "Delete multiple customers", description = "Removes multiple customers from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteCustomers(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty.");
            }
            customerService.deleteCustomer(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CUSTOMER_DELETED);
        });
    }

    // 获取客户详情
    @Operation(summary = "Get customer by ID", description = "Retrieves the details of a specific customer by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        return handleRequest(() -> {
            CustomerDTO customer = customerService.getCustomerById(id);
            return ApiResult.success(customer, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CUSTOMER_RETRIEVED);
        });
    }

    // 获取所有客户
    @Operation(summary = "Get all customers", description = "Retrieves a paginated list of all customers")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<CustomerDTO>>> searchCustomers(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return handleRequest(() -> {
            Page<CustomerDTO> customersPage = customerService.searchCustomers(searchParams, pageable);
            return ApiResult.success(customersPage, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CUSTOMERS_RETRIEVED);
        });
    }
}

