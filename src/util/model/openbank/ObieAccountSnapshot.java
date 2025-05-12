package util.model.openbank;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "account_snapshot_obie", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "snapshot_type", "snapshot_time"})
})
public class ObieAccountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "snapshot_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BalanceType type; // ClosingAvailable, InterimBooked 等

    @Column(name = "credit_debit_indicator", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditDebitIndicator creditDebitIndicator;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "snapshot_time", nullable = false)
    private OffsetDateTime snapshotTime;

    @Column(name = "source_txn_id", length = 64)
    private String sourceTxnId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // Enum definitions
    public enum BalanceType {
        ClosingAvailable, ClosingBooked, InterimAvailable, InterimBooked, OpeningAvailable, OpeningBooked
    }

    public enum CreditDebitIndicator {
        Credit, Debit
    }

    // Getters/setters omitted
}

