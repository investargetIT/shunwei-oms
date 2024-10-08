package com.shunwei.oms.goods.category.controller;

import com.shunwei.oms.common.dto.IdsRequest;
import com.shunwei.oms.common.constants.ApiConstants;
import com.shunwei.oms.common.controller.BaseController;
import com.shunwei.oms.common.dto.ApiResult;
import com.shunwei.oms.goods.category.dto.GoodsCategoryDTO;
import com.shunwei.oms.goods.category.service.GoodsCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods/categories")
@Tag(name = "商品品类管理", description = "Endpoints for managing goods categories")
public class GoodsCategoryController extends BaseController {

    private final GoodsCategoryService goodsCategoryService;

    public GoodsCategoryController(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    @PostMapping
    @Operation(summary = "Create a new category", responses = {
            @ApiResponse(description = "Category created successfully", responseCode = "201"),
            @ApiResponse(description = "Invalid input", responseCode = "400")
    })
    public ResponseEntity<ApiResult<GoodsCategoryDTO>> createCategory(@RequestBody GoodsCategoryDTO goodsCategoryDTO) {
        return handleRequest(() -> {
            GoodsCategoryDTO createdCategory = goodsCategoryService.createCategory(goodsCategoryDTO);
            return ApiResult.success(createdCategory, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORY_CREATED);
        });
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", responses = {
            @ApiResponse(description = "Category updated successfully", responseCode = "200"),
            @ApiResponse(description = "Category not found", responseCode = "404"),
            @ApiResponse(description = "Invalid input", responseCode = "400")
    })
    public ResponseEntity<ApiResult<GoodsCategoryDTO>> updateCategory(
            @PathVariable Long id,
            @RequestBody GoodsCategoryDTO goodsCategoryDTO) {
        return handleRequest(() -> {
            GoodsCategoryDTO updatedCategory = goodsCategoryService.updateCategory(id, goodsCategoryDTO);
            return ApiResult.success(updatedCategory, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORY_UPDATED);
        });
    }

    @Operation(summary = "Delete multiple goodsCategory", description = "Removes multiple goodsCategory from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteCategory(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty.");
            }
            goodsCategoryService.deleteCategory(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORY_DELETED);
        });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID", responses = {
            @ApiResponse(description = "Category retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Category not found", responseCode = "404")
    })
    public ResponseEntity<ApiResult<GoodsCategoryDTO>> getCategoryById(@PathVariable Long id) {
        return handleRequest(() -> {
            GoodsCategoryDTO category = goodsCategoryService.getCategoryById(id);
            return ApiResult.success(category, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORY_RETRIEVED);
        });
    }

    @GetMapping
    @Operation(summary = "Get all categories with optional search parameters", responses = {
            @ApiResponse(description = "Categories retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Invalid input", responseCode = "400")
    })
    public ResponseEntity<ApiResult<Page<GoodsCategoryDTO>>> getAllCategories(
            @Parameter(description = "Search parameters") @RequestParam Map<String, String> searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return handleRequest(() -> {
            Page<GoodsCategoryDTO> categoryPage = goodsCategoryService.searchCategories(searchParams, pageable);
            return ApiResult.success(categoryPage, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORIES_RETRIEVED);
        });
    }

    @GetMapping("/all")
    @Operation(summary = "Get all categories", responses = {
            @ApiResponse(description = "Categories retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Error retrieving categories", responseCode = "500")
    })
    public ResponseEntity<ApiResult<Map<String, List<String>>>> getAllCategories() {
        return handleRequest(() -> {
            Map<String, List<String>> categories = goodsCategoryService.getAllCategories();
            return ApiResult.success(categories, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_CATEGORIES_RETRIEVED);
        });
    }

}
