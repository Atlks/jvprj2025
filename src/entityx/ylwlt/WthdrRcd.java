package entityx.ylwlt;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

/**
 * 提现金额 - 订单号 会员账号 标签 VIP等级 上级代理 提现金额 到账金额 审核状态 审核人 提现时间 审核时间
 */
@Entity
@Data
@Table(name = "Wthdr_Rcd")
public class WthdrRcd {
    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname;

    @Id
    public String id;
    public String reviewer;
    public String revwStat;
    public long revwTime;
    public String tag;
    public  String vipLev;
    public String sprAgt;  //上级代理

    //    到账金额
    public BigDecimal rcv_amt;

    public BigDecimal getAmt() {
        return toBigDcmTwoDot(amt) ;
    }

    public BigDecimal amt;

    public long getTimestamp() {
        return timestamp;
    }
}
