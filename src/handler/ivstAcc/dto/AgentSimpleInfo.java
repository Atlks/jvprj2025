package handler.ivstAcc.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AgentSimpleInfo {
    private String agentAccount;                      // 当前代理账号
    private BigDecimal subRechargeAmount;             // 所有下级充值总额（不含自己）
    private BigDecimal commissionRate;                // 对应佣金比例
    private List<String> directSubAccounts;           // 直属下级账号列表
}
