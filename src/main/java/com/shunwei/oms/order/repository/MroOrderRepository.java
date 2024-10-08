package com.shunwei.oms.order.repository;

import com.shunwei.oms.order.entity.MroOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MroOrderRepository extends JpaRepository<MroOrder, Long>, JpaSpecificationExecutor<MroOrder> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MroOrder o WHERE o.id IN :ids")
    void deleteByIds(List<Long> ids);
}
