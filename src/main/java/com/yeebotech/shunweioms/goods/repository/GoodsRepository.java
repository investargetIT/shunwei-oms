package com.yeebotech.shunweioms.goods.repository;

import com.yeebotech.shunweioms.goods.entity.Goods;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Goods g WHERE g.id IN :ids")
    void deleteByIds(List<Long> ids);
}
