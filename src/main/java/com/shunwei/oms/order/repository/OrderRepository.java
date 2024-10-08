package com.shunwei.oms.order.repository;

import com.shunwei.oms.order.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Order g WHERE g.id IN :ids")
    void deleteByIds(List<Long> ids);
}
