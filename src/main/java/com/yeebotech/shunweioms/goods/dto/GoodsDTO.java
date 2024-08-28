package com.yeebotech.shunweioms.goods.dto;

import com.yeebotech.shunweioms.supplier.dto.SupplierDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GoodsDTO {
    private Long id;
    private String internalCode;
    private String externalCode;
    private String name;
    private String category;
    private String picture;
    private String brand;
    private String details;
    private String usageLocation;
    private String unit;
    private String boxStandards;
    private float costPrice;
    private float sellingPrice;
    private float grossMargin;
    private SupplierDTO supplier; // 包含 SupplierDTO
    private String leadTime;
    private int moq;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
