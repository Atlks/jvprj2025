package handler.ivstAcc.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class AgentSubInfo {
    private String agentAccount;            // 直属下级账号
    private BigDecimal rechargeAmount;      // 直属下级的充值总额
    private BigDecimal commissionRate;      // 匹配佣金比例
    private List<String> memberAccounts;    // 下下级会员账号列表
}
