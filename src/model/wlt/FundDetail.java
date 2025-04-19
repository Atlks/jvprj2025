package model.wlt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

import static util.algo.GetUti.getUUid;
@Entity
@Table(name = "fund_detail")

@Data
public class FundDetail {
    @Id
    public String id=getUUid();
    public String uname;
    public  String  wlt="本金钱包";   //保险资金池 / ylwlt
    public  String msg="充值 成功";  //兑换 成功 ,亏损 ，收益，佣金，增加，提现，减少，调整成功
    public String dscrp;  //备注
    public String img;  //截图
    public String changeMode="+";     // or  -
    public  long timestamp=System.currentTimeMillis();
    public BigDecimal changeAmount;
    public BigDecimal amtBefore;
    public BigDecimal amtAfter;

}
