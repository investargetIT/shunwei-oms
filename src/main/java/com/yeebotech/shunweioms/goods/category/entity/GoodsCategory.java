package com.yeebotech.shunweioms.goods.category.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "goods_category")
@EntityListeners(AuditingEntityListener.class)
public class GoodsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(nullable = false)
    private String parentCategory;  // 大类

    @Column(nullable = false)
    private String category;  // 中类

    private String subCategory;  // 小类

    @Column(nullable = false)
    private String name;  // 详细名称

    private String attributes;  // 分类属性

    private String others;  // 其他

    @Lob
    private String remark;  // 备注

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
