package com.shunwei.oms.customer.service;

import com.shunwei.oms.common.util.DateUtils;
import com.shunwei.oms.customer.dto.CustomerDTO;
import com.shunwei.oms.customer.entity.Customer;
import com.shunwei.oms.customer.repository.CustomerRepository;
import com.shunwei.oms.customer.service.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // 直接将 CustomerDTO 转换为 Customer 实体
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        // 保存到数据库
        Customer savedCustomer = customerRepository.save(customer);
        return toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            BeanUtils.copyProperties(customerDTO, customer, "id", "createdAt", "updatedAt");
            customer.setUpdatedAt(LocalDateTime.now());
            Customer updatedCustomer = customerRepository.save(customer);
            return toDTO(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }


    @Override
    public void deleteCustomer(List<Long> ids) {
        customerRepository.deleteByIds(ids);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::toDTO)
                .orElse(null); // or throw an exception
    }

    @Override
    public Page<CustomerDTO> searchCustomers(Map<String, String> searchParams, Pageable pageable) {
        Specification<Customer> spec = Specification.where(null);

        if (searchParams.containsKey("name")) {
            spec = spec.and(CustomerSpecification.hasName(searchParams.get("name")));
        }
        if (searchParams.containsKey("code")) {
            spec = spec.and(CustomerSpecification.hasCode(searchParams.get("code")));
        }
        if (searchParams.containsKey("province")) {
            spec = spec.and(CustomerSpecification.hasProvince(searchParams.get("province")));
        }
        if (searchParams.containsKey("city")) {
            spec = spec.and(CustomerSpecification.hasCity(searchParams.get("city")));
        }
        if (searchParams.containsKey("district")) {
            spec = spec.and(CustomerSpecification.hasDistrict(searchParams.get("district")));
        }
        if (searchParams.containsKey("telephone")) {
            spec = spec.and(CustomerSpecification.hasTelephone(searchParams.get("telephone")));
        }
        if (searchParams.containsKey("status")) {
            spec = spec.and(CustomerSpecification.hasStatus(searchParams.get("status")));
        }
        if (searchParams.containsKey("brand")) {
            spec = spec.and(CustomerSpecification.hasBrand(searchParams.get("brand")));
        }
        if (searchParams.containsKey("invoiceType")) {
            spec = spec.and(CustomerSpecification.hasInvoiceType(searchParams.get("invoiceType")));
        }
        if (searchParams.containsKey("createdAt")) {
            LocalDateTime createdAt = DateUtils.parseLocalDateTime(searchParams.get("createdAt"));
            spec = spec.and(CustomerSpecification.hasCreatedAt(createdAt));
        }
        if (searchParams.containsKey("updatedAt")) {
            LocalDateTime updatedAt = DateUtils.parseLocalDateTime(searchParams.get("updatedAt"));
            spec = spec.and(CustomerSpecification.hasUpdatedAt(updatedAt));
        }

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );
        Page<Customer> customerPage = customerRepository.findAll(spec, sortedPageable);
        return customerPage.map(this::toDTO);
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }
}
