package model.OpenBankingOBIE;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import model.obieErrCode.FieldInvalidEx;

import java.math.BigDecimal;
@Data
public class ChargeAmount {

    @NotNull
    @JsonProperty("Amount")
    private BigDecimal chgAmtAmount;

    @NotNull
    @Size(min = 3, max = 3)
    @JsonProperty("Currency")
    private String chgAmtCurrency="usdt";

    public ChargeAmount(BigDecimal chargeAmount1) throws FieldInvalidEx {
        if (chargeAmount1 == null || chargeAmount1.compareTo(BigDecimal.ZERO) < 0)
            throw new FieldInvalidEx("ChargeAmount="+chargeAmount1.toString());

        this.chgAmtAmount = chargeAmount1;
    }
}
