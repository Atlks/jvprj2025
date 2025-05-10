package model.rpt_dataSmry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 *  月份充值统计实体类
 *  * 包含以下字段：
 *  * - 月份（如：2025-05）
 *  * - 充值总金额
 *  * - 充值人数
 * 月份 充值总金额  充值人数
 */
@Entity
@Table(name="month_rechg_stat_rpt")
@Data
public class MonthRechgStats {


  /**
   * 月份（格式如：2025-05）
   */
  @Id
  private String yr_month;

 /*
  * 该月充值总金额

  */
  public   BigDecimal MonthRechargeAmount;

  public   long  MonthRechargeUsersCnt;
}
