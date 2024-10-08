package com.shunwei.oms.goods.service;

import com.shunwei.oms.goods.dto.GoodsDTO;
import com.shunwei.oms.goods.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GoodsService {

    Page<GoodsDTO> searchGoods(Map<String, Object> searchParams, Pageable pageable);

    Optional<Goods> getGoodsById(Long id);

    Goods createGoods(Goods goods);

    Optional<Goods> updateGoods(Long id, Goods goods);

    void deleteGoods(List<Long> ids);
    // New method for converting Goods to GoodsDTO
    GoodsDTO goodsToDTO(Goods goods);
}
