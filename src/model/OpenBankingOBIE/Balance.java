package model.OpenBankingOBIE;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import util.model.openbank.BalanceType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
@Entity
@Data
@Table(name="balances")
@FieldNameConstants
public class Balance {

    public String accountId;
    private BigDecimal amount;
    private String currency;
    //   Wallet.BalanceType
    //   private BalanceAmount balanceAmount;

    //   balanceType: 说明该余额的类型，例如“AVAILABLE”表示可用余额，“CURRENT”表示当前余额。

    //  dateTime: 余额记录的时间戳，格式为 ISO 8601。
    @Enumerated(EnumType.STRING)
    private BalanceType balanceType;
    private ZonedDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private CreditDebitIndicator creditDebitIndicator; // Credit or Debit

    public Balance(String accid) {
        this.accountId=accid;
    }
    // private CreditLine creditLine;  crdt card bls
}
