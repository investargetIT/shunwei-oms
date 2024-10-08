package com.shunwei.oms.customer.service.specification;

import com.shunwei.oms.customer.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CustomerSpecification {

    public static Specification<Customer> hasName(String name) {
        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Customer> hasCode(String code) {
        return (root, query, builder) ->
                builder.like(root.get("code"), "%" + code + "%");
    }

    public static Specification<Customer> hasProvince(String province) {
        return (root, query, builder) ->
                builder.like(root.get("province"), "%" + province + "%");
    }

    public static Specification<Customer> hasCity(String city) {
        return (root, query, builder) ->
                builder.like(root.get("city"), "%" + city + "%");
    }

    public static Specification<Customer> hasDistrict(String district) {
        return (root, query, builder) ->
                builder.like(root.get("district"), "%" + district + "%");
    }

    public static Specification<Customer> hasTelephone(String telephone) {
        return (root, query, builder) ->
                builder.like(root.get("telephone"), "%" + telephone + "%");
    }

    public static Specification<Customer> hasStatus(String status) {
        return (root, query, builder) ->
                builder.equal(root.get("status"), status);
    }

    public static Specification<Customer> hasBrand(String brand) {
        return (root, query, builder) ->
                builder.like(root.get("brand"), "%" + brand + "%");
    }

    public static Specification<Customer> hasInvoiceType(String invoiceType) {
        return (root, query, builder) ->
                builder.equal(root.get("invoiceType"), invoiceType);
    }

    public static Specification<Customer> hasCreatedAt(LocalDateTime createdAt) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }

    public static Specification<Customer> hasUpdatedAt(LocalDateTime updatedAt) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("updatedAt"), updatedAt);
    }
}
