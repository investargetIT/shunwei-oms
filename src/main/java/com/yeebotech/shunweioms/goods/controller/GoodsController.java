package com.yeebotech.shunweioms.goods.controller;

import com.yeebotech.shunweioms.common.constants.ApiConstants;
import com.yeebotech.shunweioms.common.controller.BaseController;
import com.yeebotech.shunweioms.common.dto.ApiResult;
import com.yeebotech.shunweioms.common.dto.IdsRequest;
import com.yeebotech.shunweioms.goods.dto.GoodsDTO;
import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Operation(summary = "Search for goods")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<GoodsDTO>>> searchGoods(@RequestParam Map<String, Object> searchParams,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return handleRequest(() -> {
            Page<GoodsDTO> goodsPage = goodsService.searchGoods(searchParams, pageable);
            return ApiResult.success(goodsPage, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_GOODS_RETRIEVED);
        });
    }

    @Operation(summary = "Get a specific goods by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<Goods>> getGoodsById(@PathVariable Long id) {
        return handleRequest(() -> goodsService.getGoodsById(id)
                .map(goods -> ApiResult.success(goods, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_GOODS_RETRIEVED))
                .orElse(ApiResult.error(ApiConstants.CODE_NOT_FOUND, ApiConstants.MESSAGE_NO_GOODS_WITH_ID, null)));
    }

    @Operation(summary = "Create a new goods")
    @PostMapping
    public ResponseEntity<ApiResult<Goods>> createGoods(@RequestBody Goods goods) {
        return handleRequest(() -> {
            Goods createdGoods = goodsService.createGoods(goods);
            return ApiResult.success(createdGoods, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_GOODS_CREATED);
        });
    }

    @Operation(summary = "Update a goods")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<Goods>> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        return handleRequest(() -> goodsService.updateGoods(id, goods)
                .map(updatedGoods -> ApiResult.success(updatedGoods, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_GOODS_UPDATED))
                .orElse(ApiResult.error(ApiConstants.CODE_NOT_FOUND, ApiConstants.MESSAGE_NO_GOODS_WITH_ID, null)));
    }

    @Operation(summary = "Delete multiple goods", description = "Removes multiple goods from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteGoods(@RequestBody IdsRequest idsRequest) {
        return handleRequest(() -> {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty.");
            }
            goodsService.deleteGoods(ids);
            return ApiResult.success(null, ApiConstants.CODE_BUSINESS_SUCCESS, ApiConstants.MESSAGE_SUCCESS_GOODS_DELETED);
        });
    }
}
