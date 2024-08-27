package com.yeebotech.shunweioms.supplier.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true) // 隐藏 id 字段
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "partnership_case")
    private String partnershipCase;

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "mode")
    private String mode;

    @Column(name = "hotel")
    private String hotel;

    @Column(name = "status")
    private String status;

    @Column(name = "contact")
    private String contact;

    @Column(name = "position")
    private String position;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "salesman")
    private String salesman;

    @Column(name = "contract_status")
    private String contractStatus;

    @Column(name = "deal_date")
    private LocalDate dealDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    @Column(name = "created_at", updatable = false)
    @Schema(hidden = true) // 隐藏 createdAt 字段
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(hidden = true) // 隐藏 updatedAt 字段
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
