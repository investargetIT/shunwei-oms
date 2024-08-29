package com.yeebotech.shunweioms.goods.controller;

import com.yeebotech.shunweioms.common.constants.ApiConstants;
import com.yeebotech.shunweioms.common.dto.ApiResult;
import com.yeebotech.shunweioms.common.dto.IdsRequest;
import com.yeebotech.shunweioms.goods.dto.GoodsDTO;
import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Operation(summary = "Search for goods")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<GoodsDTO>>> searchGoods(@RequestParam Map<String, Object> searchParams,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<GoodsDTO> goodsPage = goodsService.searchGoods(searchParams, pageable);
            ApiResult<Page<GoodsDTO>> response = ApiResult.success(
                    goodsPage,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_GOODS_RETRIEVED
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResult<Page<GoodsDTO>> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_RETRIEVE_GOODS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a specific goods by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<Goods>> getGoodsById(@PathVariable Long id) {
        try {
            Optional<Goods> goods = goodsService.getGoodsById(id);
            if (goods.isPresent()) {
                ApiResult<Goods> response = ApiResult.success(
                        goods.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_GOODS_RETRIEVED
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResult<Goods> response = ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_GOODS_WITH_ID,
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResult<Goods> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_RETRIEVE_GOODS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new goods")
    @PostMapping
    public ResponseEntity<ApiResult<Goods>> createGoods(@RequestBody Goods goods) {


        try {
            Goods createdGoods = goodsService.createGoods(goods);
            ApiResult<Goods> response = ApiResult.success(
                    createdGoods,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_GOODS_CREATED
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResult<Goods> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_CREATE_GOODS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a goods")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<Goods>> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        try {
            Optional<Goods> updatedGoods = goodsService.updateGoods(id, goods);
            if (updatedGoods.isPresent()) {
                ApiResult<Goods> response = ApiResult.success(
                        updatedGoods.get(),
                        ApiConstants.CODE_BUSINESS_SUCCESS,
                        ApiConstants.MESSAGE_SUCCESS_GOODS_UPDATED
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResult<Goods> response = ApiResult.error(
                        ApiConstants.CODE_NOT_FOUND,
                        ApiConstants.MESSAGE_NO_GOODS_WITH_ID,
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResult<Goods> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_UPDATE_GOODS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete multiple goods", description = "Removes multiple goods from the system")
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResult<Void>> deleteGoods(@RequestBody IdsRequest idsRequest) {
        try {
            List<Long> ids = idsRequest.getIds();
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResult.error(ApiConstants.CODE_BAD_REQUEST, "IDs are required", "The provided IDs list is either null or empty."));
            }
            goodsService.deleteGoods(ids);
            ApiResult<Void> response = ApiResult.success(
                    null,
                    ApiConstants.CODE_BUSINESS_SUCCESS,
                    ApiConstants.MESSAGE_SUCCESS_GOODS_DELETED
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            ApiResult<Void> response = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    ApiConstants.MESSAGE_FAILED_TO_DELETE_GOODS,
                    e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
