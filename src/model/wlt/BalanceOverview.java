package model.wlt;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceOverview {
    public BigDecimal accBalance;
    public BigDecimal accInvst_balance;
    public BigDecimal InsFdPool_balance;
}
