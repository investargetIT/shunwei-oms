package com.yeebotech.shunweioms.repository;

import com.yeebotech.shunweioms.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Custom query methods can be added here if needed
}
