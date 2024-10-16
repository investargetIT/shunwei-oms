package com.shunwei.oms.goods.category.repository;

import com.shunwei.oms.goods.category.entity.GoodsCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long>, JpaSpecificationExecutor<GoodsCategory> {
    @Modifying
    @Transactional
    @Query("DELETE FROM GoodsCategory g WHERE g.id IN :ids")
    void deleteByIds(List<Long> ids);

    // 根据分类名称、子分类名称和具体分类名称查找分类 ID
    @Query("SELECT gc.id FROM GoodsCategory gc WHERE gc.category = :categoryName AND gc.subCategory = :subCategoryName AND gc.parentCategory = :parentCategoryName")
    Long findIdByDetails(String categoryName, String subCategoryName, String parentCategoryName);
}
