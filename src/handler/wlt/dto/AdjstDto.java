package handler.wlt.dto;


/**
 *
 * 实体类名称：资金调整dto
 * 包含以下字段 ：：
 * 额度类型
 *
 * 调整类型
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
public class AdjstDto {

    /**
     * 额度类型（如：本金、奖励金等）
     */
    public String quotaType;

    /**
     * 调整类型（如：增加、减少、冻结、解冻）
     */
    public String adjustType;

    /**
     * 会员账号（用户唯一标识）
     */
    public String memberAccount;

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

    // Getter & Setter 省略可用 lombok 代替，如 @Data
    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType;
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

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
