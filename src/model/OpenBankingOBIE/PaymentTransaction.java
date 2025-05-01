package model.OpenBankingOBIE;

import jakarta.persistence.*;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    @Enumerated(EnumType.STRING)
    private TransactionStatusCode status;


    /**
     *  强调“用户指令”而非实际结算金额
     * InstructedAmount 是用户 “指令中要求支付的金额”。
     *
     * 它 未必等于 实际到账金额（例如涉及手续费、货币转换、拒绝等情况）。
     *
     * 银行或支付系统有可能最终执行的是不同的金额（例如部分支付、退款等）
     */
    private String  InstructedAmount;//标准字段
    private String amount;//非标字段
    private String currency;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public TransactionStatusCode getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusCode status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

