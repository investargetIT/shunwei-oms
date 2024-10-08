package com.shunwei.oms.goods.category.service.impl;

import com.shunwei.oms.goods.category.entity.GoodsCategory;
import com.shunwei.oms.goods.category.repository.GoodsCategoryRepository;
import com.shunwei.oms.goods.category.dto.GoodsCategoryDTO;
import com.shunwei.oms.goods.category.service.GoodsCategoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private final GoodsCategoryRepository goodsCategoryRepository;

    public GoodsCategoryServiceImpl(GoodsCategoryRepository goodsCategoryRepository) {
        this.goodsCategoryRepository = goodsCategoryRepository;
    }

    @Override
    public GoodsCategoryDTO createCategory(GoodsCategoryDTO goodsCategoryDTO) {
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryDTO, goodsCategory);
        GoodsCategory savedCategory = goodsCategoryRepository.save(goodsCategory);
        return convertToDTO(savedCategory);
    }

    @Override
    public GoodsCategoryDTO updateCategory(Long id, GoodsCategoryDTO goodsCategoryDTO) {
        Optional<GoodsCategory> optionalCategory = goodsCategoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            GoodsCategory goodsCategory = optionalCategory.get();
            BeanUtils.copyProperties(goodsCategoryDTO, goodsCategory, "id", "createdAt");
            GoodsCategory updatedCategory = goodsCategoryRepository.save(goodsCategory);
            return convertToDTO(updatedCategory);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Override
    public void deleteCategory(List<Long> ids) {
        goodsCategoryRepository.deleteByIds(ids);
    }

    @Override
    public GoodsCategoryDTO getCategoryById(Long id) {
        Optional<GoodsCategory> category = goodsCategoryRepository.findById(id);
        if (category.isPresent()) {
            return convertToDTO(category.get());
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Override
    public Page<GoodsCategoryDTO> searchCategories(Map<String, String> searchParams, Pageable pageable) {
        Specification<GoodsCategory> specification = (Root<GoodsCategory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 根据大类进行模糊搜索
            String parentCategory = searchParams.get("parentCategory");
            if (StringUtils.hasText(parentCategory)) {
                predicates.add(criteriaBuilder.like(root.get("parentCategory"), "%" + parentCategory + "%"));
            }

            // 根据中类进行模糊搜索
            String category = searchParams.get("category");
            if (StringUtils.hasText(category)) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + category + "%"));
            }

            // 根据小类进行模糊搜索
            String subCategory = searchParams.get("subCategory");
            if (StringUtils.hasText(subCategory)) {
                predicates.add(criteriaBuilder.like(root.get("subCategory"), "%" + subCategory + "%"));
            }

            // 根据详细名称进行模糊搜索
            String name = searchParams.get("name");
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            // 根据属性进行模糊搜索
            String attributes = searchParams.get("attributes");
            if (StringUtils.hasText(attributes)) {
                predicates.add(criteriaBuilder.like(root.get("attributes"), "%" + attributes + "%"));
            }

            // 根据备注进行模糊搜索
            String remark = searchParams.get("remark");
            if (StringUtils.hasText(remark)) {
                predicates.add(criteriaBuilder.like(root.get("remark"), "%" + remark + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );

        return goodsCategoryRepository.findAll(specification, sortedPageable)
                .map(this::convertToDTO);
    }

    private GoodsCategoryDTO convertToDTO(GoodsCategory goodsCategory) {
        GoodsCategoryDTO dto = new GoodsCategoryDTO();
        BeanUtils.copyProperties(goodsCategory, dto);
        return dto;
    }

    @Override
    public Map<String, List<String>> getAllCategories() {
        List<GoodsCategory> categories = goodsCategoryRepository.findAll();

        // 用于存储去重后的字段值
        Set<String> parentCategories = new HashSet<>();
        Set<String> categoriesSet = new HashSet<>();
        Set<String> subCategories = new HashSet<>();
        Set<String> names = new HashSet<>();

        // 遍历分类并收集去重的值，过滤空字符串
        for (GoodsCategory category : categories) {
            if (category.getParentCategory() != null && !category.getParentCategory().isEmpty()) {
                parentCategories.add(category.getParentCategory());
            }
            if (category.getCategory() != null && !category.getCategory().isEmpty()) {
                categoriesSet.add(category.getCategory());
            }
            if (category.getSubCategory() != null && !category.getSubCategory().isEmpty()) {
                subCategories.add(category.getSubCategory());
            }
            if (category.getName() != null && !category.getName().isEmpty()) {
                names.add(category.getName());
            }
        }

        // 构建返回结果
        Map<String, List<String>> result = new HashMap<>();
        result.put("parentCategory", new ArrayList<>(parentCategories));
        result.put("category", new ArrayList<>(categoriesSet));
        result.put("subCategory", new ArrayList<>(subCategories));
        result.put("name", new ArrayList<>(names));

        return result;
    }


}
