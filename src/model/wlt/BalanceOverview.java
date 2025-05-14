package model.wlt;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceOverview {
    public BigDecimal accBalance= BigDecimal.valueOf(0);
    public BigDecimal accInvst_balance= BigDecimal.valueOf(0);
    public BigDecimal InsFdPool_balance= BigDecimal.valueOf(0);
}
