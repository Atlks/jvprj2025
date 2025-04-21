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
public class BalancesFundDetail {
    @Id
    public String id=getUUid();
    public String uname="";
    public  String  wlt="本金钱包";   //保险资金池 / ylwlt
    public  String msg="充值 成功";  //兑换 成功 ,亏损 ，收益，佣金，增加，提现，减少，调整成功
    public String dscrp="";  //备注
    public String img="";  //截图
    public String changeMode="+";     // or  -
    public String changeType=""; //   充值  //


    //收支类型
    public String IncomeExpendType="";  //收入



    public  long timestamp=System.currentTimeMillis();
    public BigDecimal changeAmount= BigDecimal.valueOf(0);
    public BigDecimal amtBefore= BigDecimal.valueOf(0);
    public BigDecimal amtAfter= BigDecimal.valueOf(0);

    //关联订单号
    public String  rlt_ord_id;

       //     账变场景(充值成功 兑换  通过资金调整，加额 盈利20%转入 领取佣金 提现成功 投资亏损)

    public String changeReason;

}
