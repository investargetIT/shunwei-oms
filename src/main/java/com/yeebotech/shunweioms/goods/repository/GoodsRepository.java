package com.yeebotech.shunweioms.goods.repository;

import com.yeebotech.shunweioms.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
