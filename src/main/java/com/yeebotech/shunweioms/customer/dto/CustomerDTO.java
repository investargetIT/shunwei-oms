package com.yeebotech.shunweioms.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String code;
    private String name;
    private String province;
    private String city;
    private String district;
    private String address;
    private String contact;
    private String telephone;
    private String status;
    private String brand;
    private String attribute;
    private String invoiceType;
    private String invoiceTitle;
    private String invoiceTitleTin;
    private String invoiceTitleAddress;
    private String invoiceTitleBank;
    private String invoiceTitleBankAccount;
    private String invoiceTitleTelephone;
    private String invoiceReceiver;
    private String invoiceReceiverTel;
    private String invoiceReceiverAddress;
    private String invoiceReceiverEmail;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
