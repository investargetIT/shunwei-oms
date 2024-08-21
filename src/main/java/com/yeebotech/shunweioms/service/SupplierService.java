package com.yeebotech.shunweioms.service;

import com.yeebotech.shunweioms.entity.Supplier;
import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> findAllSuppliers();
    Optional<Supplier> findSupplierById(Long id);
    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> updateSupplier(Long id, Supplier supplier);
    boolean deleteSupplier(Long id);
}
