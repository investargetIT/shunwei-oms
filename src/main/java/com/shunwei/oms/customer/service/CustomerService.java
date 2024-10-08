package com.shunwei.oms.customer.service;

import com.shunwei.oms.customer.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomer(List<Long> ids);

    CustomerDTO getCustomerById(Long id);

    Page<CustomerDTO> searchCustomers(Map<String, String> searchParams, Pageable pageable);
}
