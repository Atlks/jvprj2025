package model.wlt;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceOverview {
    public BigDecimal balance;
    public BigDecimal accYlwlt_balance;
    public BigDecimal InsFdPool_balance;
}
