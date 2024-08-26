package com.yeebotech.shunweioms.goods.controller;

import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Operation(summary = "Get all goods")
    @GetMapping
    public ResponseEntity<List<Goods>> getAllGoods() {
        return ResponseEntity.ok(goodsService.getAllGoods());
    }

    @Operation(summary = "Get a specific good by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long id) {
        return ResponseEntity.ok(goodsService.getGoodsById(id));
    }

    @Operation(summary = "Create a new good")
    @PostMapping
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        return ResponseEntity.ok(goodsService.createGoods(goods));
    }

    @Operation(summary = "Update a good")
    @PutMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        return ResponseEntity.ok(goodsService.updateGoods(id, goods));
    }

    @Operation(summary = "Delete a good")
    @ApiResponse(responseCode = "204", description = "Goods deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.noContent().build();
    }
}
