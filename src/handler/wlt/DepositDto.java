package handler.wlt;

import model.OpenBankingOBIE.TransactionCode;

import java.math.BigDecimal;

public class DepositDto {
    public String accid;
    public BigDecimal amt;
    public TransactionCode type;
}
