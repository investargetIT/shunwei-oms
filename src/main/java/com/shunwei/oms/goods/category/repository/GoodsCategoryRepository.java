package com.shunwei.oms.goods.category.repository;

import com.shunwei.oms.goods.category.entity.GoodsCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long>, JpaSpecificationExecutor<GoodsCategory> {
    @Modifying
    @Transactional
    @Query("DELETE FROM GoodsCategory g WHERE g.id IN :ids")
    void deleteByIds(List<Long> ids);
}
