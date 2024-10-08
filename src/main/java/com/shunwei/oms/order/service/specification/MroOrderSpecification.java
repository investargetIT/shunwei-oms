package com.shunwei.oms.order.service.specification;

import com.shunwei.oms.order.entity.MroOrder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MroOrderSpecification {

    public static Specification<MroOrder> bySearchParams(Map<String, String> searchParams) {
        return (Root<MroOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParams != null && !searchParams.isEmpty()) {
                for (Map.Entry<String, String> entry : searchParams.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    switch (key) {
                        case "orderNumber":
                            // 根据订单号查询
                            predicates.add(builder.like(root.get("orderNumber"), "%" + value + "%"));
                            break;
                        case "customerName":
                            // 根据客户名称查询
                            predicates.add(builder.like(root.get("customer").get("name"), "%" + value + "%"));
                            break;
                        case "status":
                            // 根据订单状态查询
                            predicates.add(builder.equal(root.get("status"), Integer.parseInt(value)));
                            break;
                        case "createdAt":
                            // 根据创建时间查询 (假设传递的是日期范围)
                            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), value));
                            break;
                        case "updatedAt":
                            // 根据更新时间查询 (假设传递的是日期范围)
                            predicates.add(builder.lessThanOrEqualTo(root.get("updatedAt"), value));
                            break;
                        default:
                            break;
                    }
                }
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
