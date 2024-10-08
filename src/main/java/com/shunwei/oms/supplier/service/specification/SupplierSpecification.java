package com.shunwei.oms.supplier.service.specification;

import com.shunwei.oms.supplier.entity.Supplier;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SupplierSpecification {

    public static Specification<Supplier> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Supplier> hasCode(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("code"), "%" + code + "%");
    }

    public static Specification<Supplier> hasBankAccount(String bankAccount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("bankAccount"), "%" + bankAccount + "%");
    }

    public static Specification<Supplier> hasPartnershipCase(String partnershipCase) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("partnershipCase"), "%" + partnershipCase + "%");
    }

    public static Specification<Supplier> hasAttribute(String attribute) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("attribute"), "%" + attribute + "%");
    }

    public static Specification<Supplier> hasMode(String mode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("mode"), "%" + mode + "%");
    }

    public static Specification<Supplier> hasHotel(String hotel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("hotel"), "%" + hotel + "%");
    }

    public static Specification<Supplier> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("status"), "%" + status + "%");
    }

    public static Specification<Supplier> hasContact(String contact) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("contact"), "%" + contact + "%");
    }

    public static Specification<Supplier> hasPosition(String position) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("position"), "%" + position + "%");
    }

    public static Specification<Supplier> hasTelephone(String telephone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("telephone"), "%" + telephone + "%");
    }

    public static Specification<Supplier> hasSalesman(String salesman) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("salesman"), "%" + salesman + "%");
    }

    public static Specification<Supplier> hasContractStatus(String contractStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("contractStatus"), "%" + contractStatus + "%");
    }

    public static Specification<Supplier> hasDealDate(LocalDate dealDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dealDate"), dealDate);
    }

    public static Specification<Supplier> hasStartDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("startDate"), startDate);
    }

    public static Specification<Supplier> hasEndDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("endDate"), endDate);
    }

    public static Specification<Supplier> hasRemark(String remark) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("remark"), "%" + remark + "%");
    }

    public static Specification<Supplier> hasCreatedAt(LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdAt"), createdAt);
    }

    public static Specification<Supplier> hasUpdatedAt(LocalDateTime updatedAt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("updatedAt"), updatedAt);
    }
}
