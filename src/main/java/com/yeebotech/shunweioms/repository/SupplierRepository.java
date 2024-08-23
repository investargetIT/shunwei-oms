package com.yeebotech.shunweioms.repository;

import com.yeebotech.shunweioms.entity.Supplier;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Supplier s WHERE s.id IN :ids")
    void deleteByIds(List<Long> ids);
}