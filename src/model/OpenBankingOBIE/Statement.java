package model.OpenBankingOBIE;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statements")
public class Statement {

    @Id

    private String id= UUID.randomUUID().toString();

    private String accountId;

    private String statementId;

    /**
     * 在 OBIE（Open Banking Implementation Entity）v3 中，并没有一个字段 直接叫“Monthly Statement”或“Monthly Frequency”。但是你可以通过如下两种方式表示某个对账单是“月度对账单”：
     * 结合 StatementReference
     *
     * ✅ 方法 1：使用 StatementType = RegularPeriodic 并结合起止时间
     * statement.setStatementReference("APR-2025-MONTHLY");
     */
    private String statementReference;

    @Enumerated(EnumType.STRING)
    private StatementType type;

    @Enumerated(EnumType.STRING)
    private StatementSubType subtype;

    private OffsetDateTime startDateTime;

    private OffsetDateTime endDateTime;

    private OffsetDateTime creationDateTime;

    @ElementCollection
    private List<String> statementDescription;

    // 以下字段如果需要可以建复杂子表映射，目前略化为 JSON 字符串或保留字段名
 //   @Transient
  //  private List<StatementBenefit> statementBenefit;

  //  @Transient
  //  private List<StatementFee> statementFee;

   // @Transient
  //  private List<StatementInterest> statementInterest;

    /**
     * 如果你这个对账单是月度对账单（通过 StartDateTime 和 EndDateTime 控制），那么这些 StatementAmount 是在这一个月内的聚合。
     * ❌ 不固定为“月度”，而是该对账单周期内各类金额的汇总。
     */
    @Transient
  private List<StatementAmount> statementAmount;

  //  @Transient
 //   private List<StatementDateTime> statementDateTime;

    // Getter / Setter 省略
}

