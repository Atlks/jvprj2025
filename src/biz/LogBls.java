package biz;

import java.math.BigDecimal;


/**
 *   // 唯一标识符
 *     private Long id;
 *
 *     // 用户 ID
 *     private Long userId;
 *
 *     // 变动金额
 *     private BigDecimal changeAmount;
 *
 *     // 变动后余额
 *     private BigDecimal newBalance;
 *
 *     // 变动类型
 *     private String changeType; // 例如：RECHARGE, EXPENSE, REFUND
 *
 *     // 变动时间
 *     private LocalDateTime changeTime;
 *
 *     // 备注信息
 *     private String remark;
 */
public class LogBls {
    public String id;
    public String uname;

    public long changeTime;
    public String changeType;
    public String changeMode;
    public BigDecimal amtBefore;
    public BigDecimal changeAmount;
    public BigDecimal newBalance;
    // 备注信息
    private String remark;


}
