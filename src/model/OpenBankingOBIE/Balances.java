package model.OpenBankingOBIE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
@Data
public class Balances {

public String accountId;
        private BigDecimal amount;
        private String currency;
     //   private BalanceAmount balanceAmount;

     //   balanceType: 说明该余额的类型，例如“AVAILABLE”表示可用余额，“CURRENT”表示当前余额。

      //  dateTime: 余额记录的时间戳，格式为 ISO 8601。
        private String balanceType;
        private ZonedDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private CreditDebitIndicator  creditDebitIndicator; // Credit or Debit
       // private CreditLine creditLine;  crdt card bls
}
