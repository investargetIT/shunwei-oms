package com.yeebotech.shunweioms.order.dto;

import com.yeebotech.shunweioms.customer.dto.CustomerDTO;
import com.yeebotech.shunweioms.goods.dto.GoodsDTO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderResponseDTO {
    private Long id;
    private String code;
    private String type;
    private GoodsDTO goods;
    private String deliveryNo;
    private String deliveryNoRow;
    private String invoiceName;
    private Integer num;
    private Integer purchaseMultiple;
    private Float taxRate;
    private Float priceWithoutTax;
    private Float price;
    private Float amountBeforeDiscount;
    private Float discount;
    private Float totalAmountWithoutTax;
    private Float totalAmount;
    private String discountName;
    private String discountType;
    private String status;
    private LocalDateTime receiveTime;
    private String reviewStatus;
    private LocalDateTime reviewTime;
    private LocalDateTime returnReceiveTime;
    private CustomerDTO customer;
    private LocalDateTime createTime;
    private LocalDateTime takeTime;
    private LocalDateTime deliveryTime;
    private LocalDateTime signatureTime;
    private String remark;
    private Float commissionRate;
    private Float commission;
    private String paymentMethod;
    private String onlinePaymentTransactionNo;
    private String offlinePaymentBankInfo;
    private String platformPaymentStatus;
    private String vmiPaymentStatus;
    private Boolean isGsudaDelivery;
    private String shippingWarehouseType;
    private String services;
    private Float servicePriceAdjust;
}
