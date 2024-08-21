package com.yeebotech.shunweioms.repository;

import com.yeebotech.shunweioms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
