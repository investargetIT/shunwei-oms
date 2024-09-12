package com.yeebotech.shunweioms.order.entity;

import com.yeebotech.shunweioms.customer.entity.Customer;
import com.yeebotech.shunweioms.goods.entity.Goods;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String type;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime createTime;

    private LocalDateTime takeTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime signatureTime;

    @Lob
    private String remark;

    private Float commissionRate;

    private Float commission;

    private String paymentMethod;

    private String onlinePaymentTransactionNo;

    @Lob
    private String offlinePaymentBankInfo;

    private String platformPaymentStatus;

    private String vmiPaymentStatus;

    private Boolean isGsudaDelivery;

    private String shippingWarehouseType;

    private String services;

    private Float servicePriceAdjust;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
