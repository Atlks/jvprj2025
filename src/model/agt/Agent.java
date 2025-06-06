package model.agt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * 实体类：代理信息
 */
@Entity
@Table
@Data
@NoArgsConstructor
@FieldNameConstants
public class Agent {

    /**
     * 代理账号
     */
    @Id
    public String agentAccount;

    public Agent(String agtUname) {
        agentAccount=agtUname;
    }

    /**
     * 充值会员数
     */
    public Long rechargeMemberCount = 0L;

    /**
     * 注册会员数   //直属下级人数   directSubCount
     */
    public int drctSub_registeredMemberCount = 0;

    //间接下属总数
    public int indirectSubCount=0;
    //indirectSubCount	非直属下级人数（间接推荐的人数


    public int allMmbrCnt=0;  //总下级人数（直属 + 非直属）


     
//  @Column(name = "indirect_subordinate_count", columnDefinition = "INT DEFAULT 0 COMMENT '间接下属数'")
//  public int indirectSubordinateCount = 0;


  



public  String parent_agent_id="";

public int level=1;  //非直属下级人数：所有二级、三级、四级...但不是自己直接推荐的，都叫非直属。

  public  BigDecimal  lifetime_earnings;//  累计佣金收入（lifetime_earnings）
//
public int secondLevelCount=0;
public int thirdLevelCount;
//这种设计适合复杂返佣比例，比如一级返10%，二级返5%，三级返3% 等。
public int otherLevelCount;

    public long   timestamp=System.currentTimeMillis();
    /**
     * 标签
     */
    public String tag = "";

    /**
     * 兑换会员数
     */
    public int exchangeMemberCount = 0;

    /**
     * 提现会员数
     */
    public int withdrawalMemberCount = 0;

    /**
     * 充值金额   shud all sub
     */
    public BigDecimal rechargeAmount = BigDecimal.valueOf(0);

    /**
     * 兑换金额
     */
    public BigDecimal exchangeAmount = BigDecimal.valueOf(0);

    /**
     * 提现金额
     */
    public BigDecimal withdrawalAmount = BigDecimal.valueOf(0);

    /**
     * 本级充值金额  间间下属充值总额
     */
    public BigDecimal levelOneRechargeAmount = BigDecimal.valueOf(0);

    /**
     * 下级充值金额
     */
    public BigDecimal subLevelRechargeAmount = BigDecimal.valueOf(0);
    public BigDecimal drctlSubRchgAmtSum = BigDecimal.valueOf(0);//直接下级充值总金额

    public BigDecimal indrctlSubRchgAmtSum = BigDecimal.valueOf(0);//间接下级充值总金额

    /**
     * 总充值金额（本级 + 下级）
     */
    public BigDecimal totalRechargeAmount = BigDecimal.valueOf(0);

    /**
     * 佣金比例（如：0.05 表示 5%）
     */
    public BigDecimal commissionRate = BigDecimal.valueOf(5);

    /**
     * 佣金金额
     */
    public BigDecimal commissionAmount = BigDecimal.valueOf(0);

   //待领取佣金   代理余额（可提佣金>>drktl   acc_agt.bals
   public BigDecimal interimAvailableBalance =new BigDecimal(0);;

   public BigDecimal totalCommssionAmt=new BigDecimal(0);

    // Getters and Setters

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }



    public int getDrctSub_registeredMemberCount() {
        return drctSub_registeredMemberCount;
    }

    public void setDrctSub_registeredMemberCount(int drctSub_registeredMemberCount) {
        this.drctSub_registeredMemberCount = drctSub_registeredMemberCount;
    }

}
