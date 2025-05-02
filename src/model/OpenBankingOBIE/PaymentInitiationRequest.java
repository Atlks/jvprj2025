package model.OpenBankingOBIE;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInitiationRequest {
    private String debtorAccount;
    private BigDecimal instructedAmount;
    private String creditorAccount;
    private String creditorName;
    private String remittanceInformationUnstructured;
    private String requestedExecutionDate; // 格式: yyyy-MM-dd

}

//使用java构建实体 PaymentInitiationRequest
//📌 字段详解
//字段名	类型	含义
//debtorAccount	对象	付款人账户信息
//
//instructedAmount	对象	指示付款金额
//
//creditorAccount	对象	收款人账户信息
//
//creditorName	字符串	收款人名称
//remittanceInformationUnstructured	字符串	付款用途说明（非结构化备注）
//requestedExecutionDate	字符串	请求的执行日期（可选，格式为 yyyy-MM-dd）
