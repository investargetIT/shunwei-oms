package com.shunwei.oms.customer.repository;

import com.shunwei.oms.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c WHERE c.id IN :ids")
    void deleteByIds(List<Long> ids);
}
