package handler.wlt.dto;


import lombok.Data;

/**
 *
 * 实体类名称：资金调整dto
 * 包含以下字段 ：：
 * 额度类型    acc sub type
 *
 * 调整类型   tx code
 *
 * 会员账号
 *
 * 调整金额
 *
 * 额度余额
 *

 *
 * *调整备注
 */
@Data
public class AdjstDto {

    public String IdempotencyKey="";

   // public String accountSubType;

    /**
     * 额度类型账户（如：本金、奖励金等）
     * EMoney:本金钱包 ，GeneralInvestment 投资账户盈利钱包 ，
     */
    public String accountSubType;

    /**
     * 调整类型（如：增加、减少、冻结、解冻）
     * CRED 加款，DEBT 扣款，frz冻结，unfrz 解冻
     */
    public String transactionCode;

    /**
     * 会员账号（用户唯一标识）  accOwner
     */
    public String uname;

    /**
     * 调整金额（单位：元）
     */
    public Double adjustAmount= (double) 0;

    /**
     * 调整后的额度余额
     */
   // public Double quotaBalance= (double) 0;

    /**
     * 调整备注
     */
    public String remark;




    public Double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

//    public Double getQuotaBalance() {
//        return quotaBalance;
//    }
//
//    public void setQuotaBalance(Double quotaBalance) {
//        this.quotaBalance = quotaBalance;
//    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
