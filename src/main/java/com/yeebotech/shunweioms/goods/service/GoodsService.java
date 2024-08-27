package com.yeebotech.shunweioms.goods.service;

import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.supplier.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GoodsService {

    Page<Goods> searchGoods(Map<String, Object> searchParams, Pageable pageable);

    Optional<Goods> getGoodsById(Long id);

    Goods createGoods(Goods goods);

    Optional<Goods> updateGoods(Long id, Goods goods);

    void deleteGoods(List<Long> ids);
}
