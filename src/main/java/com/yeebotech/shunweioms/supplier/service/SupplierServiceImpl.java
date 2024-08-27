package com.yeebotech.shunweioms.supplier.service;

import com.yeebotech.shunweioms.supplier.entity.Supplier;
import com.yeebotech.shunweioms.supplier.repository.SupplierRepository;
import com.yeebotech.shunweioms.supplier.specification.SupplierSpecification;
import com.yeebotech.shunweioms.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> updateSupplier(Long id, Supplier supplier) {
        if (supplierRepository.existsById(id)) {
            supplier.setId(id);
            return Optional.of(supplierRepository.save(supplier));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteSupplier(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteSuppliers(List<Long> ids) {
        supplierRepository.deleteByIds(ids);
    }

    // 实现动态搜索
    @Override
    public Page<Supplier> searchSuppliers(Map<String, Object> searchParams, Pageable pageable) {
        Specification<Supplier> spec = Specification.where(null);

        if (searchParams.containsKey("name")) {
            spec = spec.and(SupplierSpecification.hasName((String) searchParams.get("name")));
        }
        if (searchParams.containsKey("code")) {
            spec = spec.and(SupplierSpecification.hasCode((String) searchParams.get("code")));
        }
        if (searchParams.containsKey("bankAccount")) {
            spec = spec.and(SupplierSpecification.hasBankAccount((String) searchParams.get("bankAccount")));
        }
        if (searchParams.containsKey("partnershipCase")) {
            spec = spec.and(SupplierSpecification.hasPartnershipCase((String) searchParams.get("partnershipCase")));
        }
        if (searchParams.containsKey("attribute")) {
            spec = spec.and(SupplierSpecification.hasAttribute((String) searchParams.get("attribute")));
        }
        if (searchParams.containsKey("mode")) {
            spec = spec.and(SupplierSpecification.hasMode((String) searchParams.get("mode")));
        }
        if (searchParams.containsKey("hotel")) {
            spec = spec.and(SupplierSpecification.hasHotel((String) searchParams.get("hotel")));
        }
        if (searchParams.containsKey("status")) {
            spec = spec.and(SupplierSpecification.hasStatus((String) searchParams.get("status")));
        }
        if (searchParams.containsKey("contact")) {
            spec = spec.and(SupplierSpecification.hasContact((String) searchParams.get("contact")));
        }
        if (searchParams.containsKey("position")) {
            spec = spec.and(SupplierSpecification.hasPosition((String) searchParams.get("position")));
        }
        if (searchParams.containsKey("telephone")) {
            spec = spec.and(SupplierSpecification.hasTelephone((String) searchParams.get("telephone")));
        }
        if (searchParams.containsKey("salesman")) {
            spec = spec.and(SupplierSpecification.hasSalesman((String) searchParams.get("salesman")));
        }
        if (searchParams.containsKey("contractStatus")) {
            spec = spec.and(SupplierSpecification.hasContractStatus((String) searchParams.get("contractStatus")));
        }
        if (searchParams.containsKey("dealDate")) {
            spec = spec.and(SupplierSpecification.hasDealDate((LocalDate) searchParams.get("dealDate")));
        }
        if (searchParams.containsKey("startDate")) {
            LocalDate startDate = DateUtils.parseLocalDate((String) searchParams.get("startDate"));
            spec = spec.and(SupplierSpecification.hasStartDate(startDate));
        }
        if (searchParams.containsKey("endDate")) {
            LocalDate endDate = DateUtils.parseLocalDate((String) searchParams.get("endDate"));
            spec = spec.and(SupplierSpecification.hasEndDate(endDate));
        }
        if (searchParams.containsKey("remark")) {
            spec = spec.and(SupplierSpecification.hasRemark((String) searchParams.get("remark")));
        }
        if (searchParams.containsKey("createdAt")) {
            LocalDateTime createdAt = DateUtils.parseLocalDateTime((String) searchParams.get("createdAt"));
            spec = spec.and(SupplierSpecification.hasCreatedAt(createdAt));
        }
        if (searchParams.containsKey("updatedAt")) {
            spec = spec.and(SupplierSpecification.hasUpdatedAt((LocalDateTime) searchParams.get("updatedAt")));
        }

        return supplierRepository.findAll(spec, pageable);
    }
}
