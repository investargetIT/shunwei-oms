package com.yeebotech.shunweioms.goods.service.specification;

import com.yeebotech.shunweioms.goods.entity.Goods;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodsSpecification {

    public static Specification<Goods> bySearchParams(Map<String, Object> searchParams) {
        return (Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParams != null && !searchParams.isEmpty()) {
                searchParams.forEach((key, value) -> {
                    if (value != null && StringUtils.hasText(value.toString())) {
                        switch (key) {
                            case "internalCode":
                                predicates.add(criteriaBuilder.equal(root.get("internalCode"), value));
                                break;
                            case "externalCode":
                                predicates.add(criteriaBuilder.equal(root.get("externalCode"), value));
                                break;
                            case "name":
                                predicates.add(criteriaBuilder.like(root.get("name"), "%" + value + "%"));
                                break;
                            case "category":
                                predicates.add(criteriaBuilder.equal(root.get("category"), value));
                                break;
                            case "brand":
                                predicates.add(criteriaBuilder.equal(root.get("brand"), value));
                                break;
                            case "picture":
                                predicates.add(criteriaBuilder.like(root.get("picture"), "%" + value + "%"));
                                break;
                            case "details":
                                predicates.add(criteriaBuilder.like(root.get("details"), "%" + value + "%"));
                                break;
                            case "usageLocation":
                                predicates.add(criteriaBuilder.equal(root.get("usageLocation"), value));
                                break;
                            case "unit":
                                predicates.add(criteriaBuilder.equal(root.get("unit"), value));
                                break;
                            case "boxStandards":
                                predicates.add(criteriaBuilder.equal(root.get("boxStandards"), value));
                                break;
                            case "costPrice":
                                predicates.add(criteriaBuilder.equal(root.get("costPrice"), value));
                                break;
                            case "sellingPrice":
                                predicates.add(criteriaBuilder.equal(root.get("sellingPrice"), value));
                                break;
                            case "grossMargin":
                                predicates.add(criteriaBuilder.equal(root.get("grossMargin"), value));
                                break;
                            case "goodsCategoryId":
                                predicates.add(criteriaBuilder.equal(root.get("goodsCategoryId"), value));
                                break;
                            case "supplierId":
                                predicates.add(criteriaBuilder.equal(root.get("supplierId"), value));
                                break;
                            case "leadTime":
                                predicates.add(criteriaBuilder.equal(root.get("leadTime"), value));
                                break;
                            case "moq":
                                predicates.add(criteriaBuilder.equal(root.get("moq"), value));
                                break;
                            case "remark":
                                predicates.add(criteriaBuilder.like(root.get("remark"), "%" + value + "%"));
                                break;
                            // Add more cases if there are other fields
                        }
                    }
                });
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
