package com.shunwei.oms.goods.category.dto;

import lombok.Data;

@Data
public class GoodsCategoryDTO {
    private Long id;
    private String parentCategory;
    private String category;
    private String subCategory;
    private String name;
    private String attributes;
    private String others;
    private String remark;
}
