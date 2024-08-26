package com.yeebotech.shunweioms.goods.entity;

import com.yeebotech.shunweioms.entity.Supplier;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_code", nullable = false, unique = true)
    private String internalCode;

    @Column(name = "external_code", nullable = false, unique = true)
    private String externalCode;

    @Column(nullable = false)
    private String name;

    private String category;
    private String picture;
    private String brand;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "usage_location")
    private String usageLocation;

    private String unit;

    @Column(name = "box_standards")
    private String boxStandards;

    @Column(name = "cost_price")
    private float costPrice;

    @Column(name = "selling_price")
    private float sellingPrice;

    @Column(name = "gross_margin")
    private float grossMargin;

    @JoinColumn(name = "supplier_id", nullable = false)
    private Long supplier;

    @Column(name = "lead_time")
    private String leadTime;

    private int moq;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
