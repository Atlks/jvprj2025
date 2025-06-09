//package model.OpenBankingOBIE;
//
//import java.math.BigDecimal;
//
//import jakarta.persistence.Embeddable;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//@Embeddable
//@Data
//public class Amount {
//
//    @NotNull
//    @JsonProperty("Amount")
//    private BigDecimal amount;
//
//    @NotNull
//    @Size(min = 3, max = 3)
//    @JsonProperty("Currency")
//    private String currency;
//
//    public Amount() {}
//
//    public Amount(BigDecimal amount, String currency) {
//        this.amount = amount;
//        this.currency = currency;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    @Override
//    public String toString() {
//        return amount + " " + currency;
//    }
//}
