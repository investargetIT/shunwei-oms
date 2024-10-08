package com.shunwei.oms.supplier.service;

import com.shunwei.oms.supplier.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SupplierService {
    Optional<Supplier> findSupplierById(Long id);
    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> updateSupplier(Long id, Supplier supplier);
    boolean deleteSupplier(Long id);

    // 新增批量删除方法
    void deleteSuppliers(List<Long> ids);

    Page<Supplier> searchSuppliers(Map<String, Object> searchParams, Pageable pageable);
    List<Supplier> findAllSuppliers();
}
