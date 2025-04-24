package model.OpenBankingOBIE;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
@Data
public class Balances {


        private BigDecimal amount;
        private String currency;
     //   private BalanceAmount balanceAmount;

     //   balanceType: 说明该余额的类型，例如“AVAILABLE”表示可用余额，“CURRENT”表示当前余额。

      //  dateTime: 余额记录的时间戳，格式为 ISO 8601。
        private String balanceType;
        private ZonedDateTime dateTime;
       // private CreditLine creditLine;  crdt card bls
}
