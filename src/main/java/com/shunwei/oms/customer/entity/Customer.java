package com.shunwei.oms.customer.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String province;
    private String city;
    private String district;
    private String address;
    private String contact;
    private String telephone;
    private String status;
    private String brand;
    private String attribute;
    private String invoiceType;
    private String invoiceTitle;

    @Column(nullable = false, unique = true)
    private String invoiceTitleTin;

    private String invoiceTitleAddress;
    private String invoiceTitleBank;
    private String invoiceTitleBankAccount;
    private String invoiceTitleTelephone;
    private String invoiceReceiver;
    private String invoiceReceiverTel;
    private String invoiceReceiverAddress;
    private String invoiceReceiverEmail;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(updatable = false)
    @Schema(hidden = true)
    private LocalDateTime createdAt;

    @Schema(hidden = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
