package model.OpenBankingOBIE;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
public class Amountx {

    @NotNull
    @JsonProperty("Amount")
    private BigDecimal amtAmount;

    @NotNull
    @Size(min = 3, max = 3)
    @JsonProperty("Currency")
    private String amtCurrency;

    public Amountx() {}

    public Amountx(BigDecimal amount, String currency) {
        this.amtAmount = amount;
        this.amtCurrency = currency;
    }

    public BigDecimal getAmount() {
        return amtAmount;
    }

    public void setAmount(BigDecimal amount) {
        this.amtAmount = amount;
    }

    public String getCurrency() {
        return amtCurrency;
    }

    public void setCurrency(String currency) {
        this.amtCurrency = currency;
    }

    @Override
    public String toString() {
        return amtAmount + " " + amtCurrency;
    }
}
