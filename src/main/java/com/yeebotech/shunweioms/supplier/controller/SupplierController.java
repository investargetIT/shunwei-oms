package com.yeebotech.shunweioms.supplier.controller;

import com.yeebotech.shunweioms.constants.ApiConstants;
import com.yeebotech.shunweioms.dto.ApiResult;
import com.yeebotech.shunweioms.dto.IdsRequest;
import com.yeebotech.shunweioms.supplier.entity.Supplier;
import com.yeebotech.shunweioms.supplier.service.SupplierService;
import com.yeebotech.shunweioms.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Retrieve a supplier by ID", description = "Fetches a supplier by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<Supplier>> getSupplierById(@PathVariable Long id) {
        try {
            Optional<Supplier> supplier = supplierService.findSupplierById(id);
            if (supplier.isPresent()) {
                ApiResult<Supplier> response = ApiResult.success(
                        supplier.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_SUPPLIER_RETRIEVED
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResult<Supplier> response = ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_SUPPLIER_WITH_ID,
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResult<Supplier> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_RETRIEVE_SUPPLIER,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new supplier", description = "Adds a new supplier to the system")
    @PostMapping
    public ResponseEntity<ApiResult<Supplier>> createSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier createdSupplier = supplierService.saveSupplier(supplier);
            ApiResult<Supplier> response = ApiResult.success(
                    createdSupplier,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIER_CREATED
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BusinessException e) {
            ApiResult<Supplier> response = ApiResult.error(
                    e.getCode(),
                    e.getMessage(),
                    e.getErrorDetails()
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResult<Supplier> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_CREATE_SUPPLIER,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an existing supplier", description = "Updates the details of a supplier")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<Supplier>> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            Optional<Supplier> updatedSupplier = supplierService.updateSupplier(id, supplier);
            if (updatedSupplier.isPresent()) {
                ApiResult<Supplier> response = ApiResult.success(
                        updatedSupplier.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_SUPPLIER_UPDATED
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResult<Supplier> response = ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_SUPPLIER_WITH_ID,
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResult<Supplier> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_UPDATE_SUPPLIER,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a supplier", description = "Removes a supplier from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> deleteSupplier(@PathVariable Long id) {
        try {
            supplierService.deleteSupplier(id);
            ApiResult<Void> response = ApiResult.success(
                    null,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIER_DELETED
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            ApiResult<Void> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_DELETE_SUPPLIER,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete multiple suppliers", description = "Removes multiple suppliers from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteSuppliers(@RequestBody IdsRequest idsRequest) {
        try {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty."));
            }
            supplierService.deleteSuppliers(ids);
            ApiResult<Void> response = ApiResult.success(
                    null,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIERS_DELETED
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            ApiResult<Void> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_DELETE_SUPPLIERS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Search for suppliers", description = "Search for suppliers based on various parameters with pagination")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<Supplier>>> searchSuppliers(
            @RequestParam Map<String, Object> searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Supplier> suppliersPage = supplierService.searchSuppliers(searchParams, pageable);
            ApiResult<Page<Supplier>> response = ApiResult.success(
                    suppliersPage,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_SUPPLIERS_RETRIEVED
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResult<Page<Supplier>> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_RETRIEVE_SUPPLIERS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Operation(summary = "Retrieve all suppliers", description = "Fetches a list of all suppliers")
//    @GetMapping
//    public ResponseEntity<ApiResult<List<Supplier>>> getAllSuppliers() {
//        try {
//            List<Supplier> suppliers = supplierService.findAllSuppliers();
//            ApiResult<List<Supplier>> response = ApiResult.success(
//                    suppliers,
//                    ApiConstants.CODE_BUSINESS_SUCCESS,
//                    ApiConstants.MESSAGE_SUCCESS_SUPPLIERS_RETRIEVED
//            );
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            ApiResult<List<Supplier>> response = ApiResult.error(
//                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
//                    ApiConstants.MESSAGE_FAILED_TO_RETRIEVE_SUPPLIERS,
//                    e.getMessage()
//            );
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

