package com.shunwei.oms.goods.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
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

    @Column(name = "supplier_id")
    private Long supplierId;


    @Column(name = "goods_category_id")
    private Long goodsCategoryId;

    @Column(name = "lead_time")
    private String leadTime;

    private int moq;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(hidden = true) // 隐藏 createdAt 字段
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Schema(hidden = true) // 隐藏 updatedAt 字段
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
