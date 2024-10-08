package com.shunwei.oms.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MroOrderResponseDTO {
    private Long id;
    private String projectCode;
    private String salesRep;
    private String clientName;
    private String orderChannel;
    private Float channelCoefficient;
    private String orderSummary;
    private LocalDate orderDate;
    private String productInquiryDescription;
    private Integer quantity;
    private String unit;
    private Float salesPricePerUnit;
    private Float salesTotalPrice;
    private Float settlementPricePerUnit;
    private Float settlementTotalPrice;
    private String recipientName;
    private String recipientPhone;
    private String deliveryAddress;
    private LocalDate requestedDeliveryDate;
    private String procurementCode;
    private Float platformAmount;
    private String supplierName;
    private String materialCode;
    private String brand;
    private String productName;
    private String productModel;
    private LocalDate supplierDeliveryTime;
    private Float purchasePrice;
    private Float purchaseTotalPrice;
    private Boolean isTaxShippingInclusive;
    private Float grossProfit;
    private Float grossMargin;
    private String paymentMethod;
    private String platformSku;
    private Boolean isOrderedOnPlatform;
    private String platformOrderNumber;
    private LocalDate supplierPaymentDate;
    private Float purchasePaymentAmount;
    private String logisticsCompany;
    private String trackingNumber;
    private String deliveryStatus;
    private LocalDate arrivalDate;
    private Boolean isInvoiceReceived;
    private String procurementInvoiceNumber;
    private Float procurementInvoiceAmount;
    private LocalDate procurementInvoiceDate;
    private String purchaseNote;
    private LocalDate billingDate;
    private String salesInvoiceNumber;
    private Float invoiceAmount;
    private LocalDate receivablesDate;
    private Boolean isPaymentReceived;
    private Float paymentReceivedAmount;
    private String adjustmentNote;
    private String saleNote;

    @Schema(hidden = true) // 隐藏 createdAt 字段
    private LocalDateTime createdAt;

    @Schema(hidden = true) // 隐藏 updatedAt 字段
    private LocalDateTime updatedAt;
}
