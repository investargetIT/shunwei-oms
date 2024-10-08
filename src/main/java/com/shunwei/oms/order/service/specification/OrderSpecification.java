package com.shunwei.oms.order.service.specification;

import com.shunwei.oms.order.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderSpecification {

    public static Specification<Order> bySearchParams(Map<String, String> searchParams) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParams != null && !searchParams.isEmpty()) {
                searchParams.forEach((key, value) -> {
                    if (StringUtils.hasText(value)) {
                        switch (key) {
                            case "code":
                                predicates.add(criteriaBuilder.equal(root.get("code"), value));
                                break;
                            case "type":
                                predicates.add(criteriaBuilder.equal(root.get("type"), value));
                                break;
                            case "status":
                                predicates.add(criteriaBuilder.equal(root.get("status"), value));
                                break;
                            case "deliveryNo":
                                predicates.add(criteriaBuilder.like(root.get("deliveryNo"), "%" + value + "%"));
                                break;
                            case "numMin":
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("num"), Integer.parseInt(value)));
                                break;
                            case "numMax":
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("num"), Integer.parseInt(value)));
                                break;
                            case "priceMin":
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Float.parseFloat(value)));
                                break;
                            case "priceMax":
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Float.parseFloat(value)));
                                break;
                            case "startDate":
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), LocalDateTime.parse(value)));
                                break;
                            case "endDate":
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), LocalDateTime.parse(value)));
                                break;
                            case "customerName":
                                predicates.add(criteriaBuilder.like(root.get("customer").get("name"), "%" + value + "%"));
                                break;
                            // Add more cases if needed
                        }
                    }
                });
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
