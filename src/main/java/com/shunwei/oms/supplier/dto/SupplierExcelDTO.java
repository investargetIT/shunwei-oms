package com.shunwei.oms.supplier.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class SupplierExcelDTO {

    @Schema(description = "供应商名称")
    private String name;

    @Schema(description = "银行账户信息")
    private String bankAccount;

    @Schema(description = "合作案例")
    private String partnershipCase;

    @Schema(description = "供应商属性")
    private String attribute;

    @Schema(description = "合作模式")
    private String mode;

    @Schema(description = "酒店销售范围")
    private String hotel;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "供应商联系人")
    private String contact;

    @Schema(description = "职位")
    private String position;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "对接人")
    private String salesman;

    @Schema(description = "合同状态")
    private String contractStatus;

    @Schema(description = "签订日期")
    private LocalDate dealDate;

    @Schema(description = "合同生效日期")
    private LocalDate startDate;

    @Schema(description = "合同终止日期")
    private LocalDate endDate;

    @Schema(description = "上传的Excel文件")
    private MultipartFile file;
}
