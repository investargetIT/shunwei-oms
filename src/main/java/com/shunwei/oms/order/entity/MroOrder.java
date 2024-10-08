package com.shunwei.oms.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mro_orders")
public class MroOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectCode;  // 项目编号

    private String salesRep;  // 销售员

    private String clientName;  // 终端单位名称

    private String orderChannel;  // 平台/线下

    private Float channelCoefficient;  // 平台系数

    @Lob
    private String orderSummary;  // 下单概况

    private LocalDate orderDate;  // 下单日期

    @Lob
    private String productInquiryDescription;  // 客户询价商品描述

    private Integer quantity;  // 数量

    private String unit;  // 单位

    private Float salesPricePerUnit;  // 销售含税单价

    private Float salesTotalPrice;  // 销售含税总价

    private Float settlementPricePerUnit;  // 结算含税单价

    private Float settlementTotalPrice;  // 结算含税总价

    private String recipientName;  // 收货人名称

    private String recipientPhone;  // 收货人电话

    private String deliveryAddress;  // 客户收货地址

    private LocalDate requestedDeliveryDate;  // 客户要求交货日期

    private String procurementCode;  // 采购编码

    private Float platformAmount;  // 平台金额

    private String supplierName;  // 供应商名称

    private String materialCode;  // 物料编码

    private String brand;  // 品牌

    private String productName;  // 产品名称

    private String productModel;  // 产品型号

    private LocalDate supplierDeliveryTime;  // 供货商交货日期

    private Float purchasePrice;  // 采购单价

    private Float purchaseTotalPrice;  // 采购总价

    private Boolean isTaxShippingInclusive;  // 是否含税含运

    private Float grossProfit;  // 毛利额

    private Float grossMargin;  // 毛利率

    private String paymentMethod;  // 付款方式

    private String platformSku;  // 平台SKU编码

    private Boolean isOrderedOnPlatform;  // 平台是否下单

    private String platformOrderNumber;  // 平台订单号

    private LocalDate supplierPaymentDate;  // 供应商付款时间

    private Float purchasePaymentAmount;  // 采购付款金额

    private String logisticsCompany;  // 物流公司

    private String trackingNumber;  // 发货物流单号

    private String deliveryStatus;  // 到货情况

    private LocalDate arrivalDate;  // 到货日期

    private Boolean isInvoiceReceived;  // 是否回票

    private String procurementInvoiceNumber;  // 采购发票号

    private Float procurementInvoiceAmount;  // 回票金额

    private LocalDate procurementInvoiceDate;  // 回票日期

    @Lob
    private String purchaseNote;  // 采购备注

    private LocalDate billingDate;  // 开票时间

    private String salesInvoiceNumber;  // 销售发票号

    private Float invoiceAmount;  // 开票金额

    private LocalDate receivablesDate;  // 应收账款时间

    private Boolean isPaymentReceived;  // 是否回款

    private Float paymentReceivedAmount;  // 回款金额

    @Lob
    private String adjustmentNote;  // 红账情况说明

    @Lob
    private String saleNote;  // 销售备注

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(hidden = true)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Schema(hidden = true)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
