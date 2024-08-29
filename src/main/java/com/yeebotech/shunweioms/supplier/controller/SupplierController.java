package com.yeebotech.shunweioms.supplier.controller;

import com.yeebotech.shunweioms.common.constants.ApiConstants;
import com.yeebotech.shunweioms.common.dto.ApiResult;
import com.yeebotech.shunweioms.common.dto.IdsRequest;
import com.yeebotech.shunweioms.supplier.entity.Supplier;
import com.yeebotech.shunweioms.supplier.service.SupplierService;
import com.yeebotech.shunweioms.common.controller.BaseController;
import com.yeebotech.shunweioms.common.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
public class SupplierController extends BaseController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Retrieve a supplier by ID", description = "Fetches a supplier by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<Supplier>> getSupplierById(@PathVariable Long id) {
        return handleRequest(() -> {
            Optional<Supplier> supplier = supplierService.findSupplierById(id);
            if (supplier.isPresent()) {
                return ApiResult.success(
                        supplier.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_SUPPLIER_RETRIEVED
                );
            } else {
                return ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_SUPPLIER_WITH_ID,
                        null
                );
            }
        });
    }

    @Operation(summary = "Create a new supplier", description = "Adds a new supplier to the system")
    @PostMapping
    public ResponseEntity<ApiResult<Supplier>> createSupplier(@RequestBody Supplier supplier) {
        return handleRequest(() -> {
            Supplier createdSupplier = supplierService.saveSupplier(supplier);
            return ApiResult.success(
                    createdSupplier,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIER_CREATED
            );
        });
    }

    @Operation(summary = "Update an existing supplier", description = "Updates the details of a supplier")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<Supplier>> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        return handleRequest(() -> {
            Optional<Supplier> updatedSupplier = supplierService.updateSupplier(id, supplier);
            if (updatedSupplier.isPresent()) {
                return ApiResult.success(
                        updatedSupplier.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_SUPPLIER_UPDATED
                );
            } else {
                return ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_SUPPLIER_WITH_ID,
                        null
                );
            }
        });
    }

    @Operation(summary = "Delete a supplier", description = "Removes a supplier from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> deleteSupplier(@PathVariable Long id) {
        return handleRequest(() -> {
            supplierService.deleteSupplier(id);
            return ApiResult.success(
                    null,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIER_DELETED
            );
        });
    }

    @Operation(summary = "Delete multiple suppliers", description = "Removes multiple suppliers from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteSuppliers(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("The provided IDs list is either null or empty.");
            }
            supplierService.deleteSuppliers(ids);
            return ApiResult.success(
                    null,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIERS_DELETED
            );
        });
    }

    @Operation(summary = "Search for suppliers", description = "Search for suppliers based on various parameters with pagination")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<Supplier>>> searchSuppliers(
            @RequestParam Map<String, Object> searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return handleRequest(() -> {
            Pageable pageable = PageRequest.of(page, size);
            Page<Supplier> suppliersPage = supplierService.searchSuppliers(searchParams, pageable);
            return ApiResult.success(
                    suppliersPage,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIERS_RETRIEVED
            );
        });
    }
}
