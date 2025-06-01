package model.OpenBankingOBIE;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import util.annos.ExtFld;
import util.annos.ObieFld;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "statements")
@FieldNameConstants
public class Statement {
    @ObieFld
    @Id
    private String statementId;

   // private String id= UUID.randomUUID().toString();
    @ObieFld
    private String accountId;


    /**
     * 在 OBIE（Open Banking Implementation Entity）v3 中，并没有一个字段 直接叫“Monthly Statement”或“Monthly Frequency”。但是你可以通过如下两种方式表示某个对账单是“月度对账单”：
     * 结合 StatementReference
     *
     * ✅ 方法 1：使用 StatementType = RegularPeriodic 并结合起止时间
     * statement.setStatementReference("APR-2025-MONTHLY");
     */
    private String statementReference;



//    @ExtFld
//    @Enumerated(EnumType.STRING)
//    private StatementSubType subtype;

    private OffsetDateTime startDateTime;

    private OffsetDateTime endDateTime;



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

    public BigDecimal rechgAmt;
    public BigDecimal transferExchgAmt;
    public BigDecimal withdrawAmt;
    public BigDecimal profitAmt;

    public void setType(StatementType statementType) {
        this.type = statementType.name().toString();
    }


    // @Enumerated(EnumType.STRING)
    // @Convert(converter = StatementTypeConverter.class)
    private String type;
    @ObieFld
    @CreationTimestamp

    @Column(nullable = false, updatable = false)
    private OffsetDateTime creationDateTime;

    //@ElementCollection
    private String statementDescription;
    //  @Transient
 //   private List<StatementDateTime> statementDateTime;

    // Getter / Setter 省略
    @ExtFld
    public String owner;

    @ExtFld
    @Column()
    public Long rechgUsersCnt=0L;
}

