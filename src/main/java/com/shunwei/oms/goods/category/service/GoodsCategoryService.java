package com.shunwei.oms.goods.category.service;

import com.shunwei.oms.goods.category.dto.GoodsCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GoodsCategoryService {
    GoodsCategoryDTO createCategory(GoodsCategoryDTO goodsCategoryDTO);

    GoodsCategoryDTO updateCategory(Long id, GoodsCategoryDTO goodsCategoryDTO);

    void deleteCategory(List<Long> ids);

    GoodsCategoryDTO getCategoryById(Long id);

    // 修改 Map 类型为 String 类型，以匹配实现类中的参数
    Page<GoodsCategoryDTO> searchCategories(Map<String, String> searchParams, Pageable pageable);

    // 新增方法获取所有商品分类
    Map<String, List<String>> getAllCategories();
}
