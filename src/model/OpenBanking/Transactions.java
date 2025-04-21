package model.OpenBanking;

import java.util.Date;

/**
 * OpenBanking的Transactions实体
 * this ,not use ,just use transPay   transWdth
 */
public class Transactions {

    // 交易唯一标识
    private String transactionId;

    // 交易类型：借记、贷记等
    private String transactionType;

    // 交易金额
    private Amount amount;

    // 商户名称（适用于消费交易）
    private String merchantName;

    // 交易的实际处理日期
    private Date bookingDate;

    // 交易金额有效入账的日期
    private Date valueDate;

    // 付款方账户信息
    private Account debtorAccount;

    // 收款方账户信息
    private Account creditorAccount;

    // 交易状态（如：COMPLETED、PENDING等）
    private String transactionStatus;

    // Getter 和 Setter 方法
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Account getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(Account debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public Account getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(Account creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    // 内部类：Amount，表示交易金额
    public static class Amount {
        private String currency;
        private double amount;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    // 内部类：Account，表示账户信息
    public static class Account {
        private String iban;
        private String currency;

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
