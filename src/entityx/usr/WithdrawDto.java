package entityx.usr;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class WithdrawDto {

    @NotBlank(message = "uname不能为空")
    public String uname;
    public Long timestamp;


    public String userId;

    @NotNull(message = "提现金额不能为空")
    @Min(value = 1, message = "提现金额必须大于0")
    public BigDecimal amount;

    //@NotBlank(message = "币种不能为空")
    private String currency; // 例如 "USD", "CNY", "THB"

    //@NotBlank(message = "提现账户不能为空")
    private String accountNumber;

    private String bankName;
    private String bankCode;

    private String status; // PENDING, SUCCESS, FAILED
    private String remark; // 备注信息

    public WithdrawDto() {}

    public WithdrawDto(String userId, BigDecimal amount, String currency, String accountNumber, String bankName, String bankCode, String status, String remark) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.status = status;
        this.remark = remark;
        this.uname=userId;
    }

    // Getter 和 Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WithdrawDto{" +
                "userId='" + userId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

