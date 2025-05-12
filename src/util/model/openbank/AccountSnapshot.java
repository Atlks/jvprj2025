package util.model.openbank;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_snapshot", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "snapshot_date"})
})
public class AccountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column(name = "balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "available_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    @Column(name = "frozen_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal frozenBalance;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "source_txn_id", length = 64)
    private String sourceTxnId;

    @Column(name = "memo", length = 255)
    private String memo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters omitted for brevity
}

