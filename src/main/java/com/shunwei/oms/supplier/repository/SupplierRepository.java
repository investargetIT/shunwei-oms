package com.shunwei.oms.supplier.repository;

import com.shunwei.oms.supplier.entity.Supplier;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Supplier s WHERE s.id IN :ids")
    void deleteByIds(List<Long> ids);

    // 定义根据供应商名称查找供应商 ID 的方法
    @Query("SELECT s.id FROM Supplier s WHERE s.name = :name")
    Long findIdByName(String name);
    boolean existsByName(String name);
}