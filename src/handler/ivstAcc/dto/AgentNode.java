package handler.ivstAcc.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import util.annos.CurrentUsername;
import util.entty.PageDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AgentNode {
    /** 当前代理账号，唯一标识 */
    private String agentAccount;
    /** 当前代理的上级代理账号，顶级代理时可能为空 */
    private String parentAgentId;
    /** 直接下级代理的充值总额 */
    private BigDecimal directSubRechargeAmount = BigDecimal.ZERO;
    /** 间接下级代理的充值总额（即二级及以下） */
    private BigDecimal indirectSubRechargeAmount = BigDecimal.ZERO;

    /** 团队总充值金额（直接下级 + 间接下级） */
    private BigDecimal totalRechargeAmount = BigDecimal.ZERO;

    /** 当前代理的佣金比例，百分比形式（如 5 表示 5%） */
    private BigDecimal commissionRate = BigDecimal.ZERO;
    /** 当前代理的佣金金额，基于 totalRechargeAmount 和比例计算 */
    private BigDecimal commissionAmount = BigDecimal.ZERO;
    /** 该代理的直接下级代理列表，形成树形结构 */
    private List<AgentNode> children = new ArrayList<>();
}
