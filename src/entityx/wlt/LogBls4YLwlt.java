package entityx.wlt;

//jakarta.persistence.Entity（JPA 3.x, Jakarta EE 9 及以后）
//javax.persistence.Entity  already dep
import entityx.usr.WithdrawDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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

@Entity
@Table
@Data
@NoArgsConstructor
public class LogBls4YLwlt {

    @Id
    public String id;
    public String uname;

    public long changeTime;
    public String changeType;
    public String changeMode;
    public BigDecimal amtBefore;
    public BigDecimal changeAmount;
    public BigDecimal newBalance;
    @NotBlank
    public String refUniqId;
    public @NotBlank String adjustType;
    // 备注信息
    private String remark;


    public LogBls4YLwlt(TransDto lgblsDto, BigDecimal nowAmt, BigDecimal newBls, String changeMode) {
this.uname=lgblsDto.uname;
        this.amtBefore = nowAmt;
        this.newBalance = newBls;
        this.changeMode=changeMode;
    }


    public LogBls4YLwlt(String  uname, BigDecimal nowAmt, BigDecimal newBls, String changeMode) {
        this.uname=uname;
        this.amtBefore = nowAmt;
        this.newBalance = newBls;
        this.changeMode=changeMode;
    }


    public LogBls4YLwlt(WithdrawDto lgblsDto, BigDecimal nowAmt, BigDecimal newBls, String changeMode) {
        this.uname=lgblsDto.getUserId();
        this.amtBefore = nowAmt;
        this.newBalance = newBls;
        this.changeMode=changeMode;
    }
}
