package handler.agt;

import com.alibaba.fastjson2.JSON;
import handler.ivstAcc.dto.AgentNode;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.agt.CmsLv;
import model.cfg.CfgKv;
import model.usr.Usr;
import handler.ivstAcc.dto.QueryDto;
import model.agt.Agent;
import org.hibernate.Session;
import util.Oosql.SlctQry;
import util.acc.AccUti;
import util.annos.Paths;
import util.model.common.ApiResponse;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.acc.AccUti.getAccid;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.*;

//  /agt/MyAgtInfo
@Path("/apiv1/agt/MyAgtInfo")
public class MyAgtInfo {


    public Object handleRequest(QueryDto reqdto) throws Throwable {
//        Agent agt= findById(Agent.class, reqdto.uname, sessionFactory.getCurrentSession());
        String agentAccount = reqdto.uname;
        String sql = "WITH RECURSIVE sub_agents AS ("
                + "SELECT * FROM agent WHERE agent_account = '" + agentAccount + "' "
                + "UNION ALL "
                + "SELECT a.* FROM agent a "
                + "INNER JOIN sub_agents sa ON a.parent_agent_id = sa.agent_account "
                + ") "
                + "SELECT * FROM sub_agents";
        Session session =  sessionFactory.getCurrentSession();
        List<Agent> list1 =getResultList(sql,Agent.class);
    return buildAgentTree(list1,agentAccount);
    }

    public static AgentNode buildAgentTree(List<Agent> allAgents, String rootAgentId) {
        // 1. 构建节点 Map
        Map<String, AgentNode> nodeMap = allAgents.stream().collect(Collectors.toMap(
                Agent::getAgentAccount,
                a -> {
                    AgentNode node = new AgentNode();
                    node.setAgentAccount(a.getAgentAccount());
                    node.setParentAgentId(a.getParent_agent_id());
                    node.setTotalRechargeAmount(Optional.ofNullable(a.getTotalRechargeAmount()).orElse(BigDecimal.ZERO));//总充值金额（直接下级+间接下级）
                    node.setCommissionRate(Optional.ofNullable(a.getCommissionRate()).orElse(BigDecimal.ZERO));//佣金比例（如：0.05 表示 5%）
                    node.setDirectSubRechargeAmount(Optional.ofNullable(a.getDrctlSubRchgAmtSum()).orElse(BigDecimal.ZERO));// 直接下级代理的充值总额
                    node.setIndirectSubRechargeAmount(Optional.ofNullable(a.getIndrctlSubRchgAmtSum()).orElse(BigDecimal.ZERO));// 间接下级代理的充值总额（即二级及以下）
                    node.setCommissionAmount(Optional.ofNullable(a.getCommissionAmount()).orElse(BigDecimal.ZERO));
                    return node;
                }
        ));

        // 2. 建立父子关系
        for (AgentNode node : nodeMap.values()) {
            String parentId = node.getParentAgentId();
            if (parentId != null && nodeMap.containsKey(parentId)) {
                nodeMap.get(parentId).getChildren().add(node);
            }
        }

        // 3. 获取根节点
        AgentNode root = nodeMap.get(rootAgentId);
        if (root == null) {
            throw new RuntimeException("未找到指定代理账号：" + rootAgentId);
        }

        List<CmsLv> cmsLvList = getCmsLvList();

        // 5. 执行递归计算汇总数据
        calculateRechargeAndCommission(root,cmsLvList);

        return root;
    }

    /**
     * 递归计算每个代理节点的团队充值金额和佣金（不包含自己充值，仅统计下级）。
     *
     * - directSubRechargeAmount：一级下级代理的总充值（子节点的 totalRechargeAmount）
     * - indirectSubRechargeAmount：二级及以下所有下级代理的充值总和
     * - totalRechargeAmount：direct + indirect
     * - commissionAmount：totalRechargeAmount × commissionRate（按百分比计算）
     *
     * @param node 当前代理节点
     */
    private static void calculateRechargeAndCommission(AgentNode node,List<CmsLv> cmsLvList) {
        BigDecimal direct = BigDecimal.ZERO;    // 一级下级的充值总和
        BigDecimal indirect = BigDecimal.ZERO;  // 二级及以下下级的充值总和

        for (AgentNode child : node.getChildren()) {
            // 递归处理每个子代理
            calculateRechargeAndCommission(child,cmsLvList);

            // 当前节点的 direct：子节点的 totalRechargeAmount
            direct = direct.add(child.getTotalRechargeAmount());

            // 当前节点的 indirect：子节点的 direct + indirect（即所有子孙）
            indirect = indirect.add(
                    child.getDirectSubRechargeAmount().add(child.getIndirectSubRechargeAmount())
            );
        }
        node.setIndirectSubRechargeAmount(indirect);
        // ✅ 动态匹配佣金比例
        BigDecimal recharge = node.getTotalRechargeAmount();
        BigDecimal commissionRate = findCommissionRate(recharge, cmsLvList);
        node.setCommissionRate(commissionRate);

        // 按百分比计算佣金（commissionRate 是百分比形式，如 5 表示 5%）
        BigDecimal commission = node.getTotalRechargeAmount()
                .multiply(node.getCommissionRate())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        node.setCommissionAmount(commission);
    }

    private static BigDecimal findCommissionRate(BigDecimal amount, List<CmsLv> cfgList) {
        BigDecimal rate = BigDecimal.ZERO;
        for (CmsLv cfg : cfgList) {
            BigDecimal cfgAmount = new BigDecimal(cfg.getRechargeAmount());
            if (amount.compareTo(cfgAmount) >= 0) {
                rate = new BigDecimal(cfg.getCommissionRate());
            } else {
                break; // 提前退出
            }
        }
        return rate;
    }


    public static List<CmsLv> getCmsLvList() {
        Session session = sessionFactory.getCurrentSession();
        try{//MbrVipCfg
            // 从 CfgKv 表中查找 key 为 "CmsLvCfg" 的配置
            CfgKv cfg = findById(CfgKv.class, "CmsLvCfg", session);
            if (cfg == null || cfg.getV() == null) {
                throw new RuntimeException("CmsLvCfg 配置未找到或为空");
            }
            // val 通常是字符串，形如："[{...}, {...}]"
            String jsonVal = cfg.getV();
            // 反序列化为 CmsLv 列表
            List<CmsLv> list = JSON.parseArray(jsonVal, CmsLv.class);
            // 按金额升序排列，确保匹配时顺序正确
            list.sort(Comparator.comparing(cfgItem -> new BigDecimal(cfgItem.getRechargeAmount())));
            return list;
        } catch (
            findByIdExptn_CantFindData e) {
            List<CmsLv> li=new ArrayList<>();
            CmsLv cfg=new CmsLv();
            li.add(cfg);
            CfgKv c=new CfgKv("CmsLvCfg",li);
            persist(c, sessionFactory.getCurrentSession());
        }
        return null;
    }


    @Paths({"/apiv1/agt/listMyMmbr"})
    public Object listMyMmbr(QueryDto reqdto) throws Throwable {
//        SlctQry query = newSelectQuery(getTableName(Usr.class));
//        //  query.select("*");
//        query.addConditions(Usr.Fields.invtr + "=" + toValStr(reqdto.uname));
//
//
//        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串

        String uname = reqdto.uname;
        // 直接拼接 uname（注意：前面已做格式校验，避免注入）
        String sqlFindUsers = """
        WITH RECURSIVE all_invited AS (
            SELECT * FROM usr WHERE uname = '%s'
            UNION ALL
            SELECT u.* FROM usr u
            INNER JOIN all_invited ai ON u.invtr = ai.uname
        )
        SELECT * FROM all_invited WHERE uname != '%s'
        """.formatted(uname, uname);
        List<Usr> users = getResultList(sqlFindUsers, Usr.class);
        if (users.isEmpty()) return users;
        List<String> unames = users.stream()
                .map(usr -> AccUti.getAccid(AccountSubType.EMoney.name(), usr.getUname()))
                .toList();
        String inClause = unames.stream()
                .map(u -> "'" + u.replace("'", "''") + "'")
                .collect(Collectors.joining(","));
        String sqlBalances = "SELECT * FROM accounts WHERE account_id IN (" + inClause + ")";
        List<Account> accountsList = getResultList(sqlBalances, Account.class);
        Map<String, BigDecimal> unameToBalanceMap = accountsList.stream()
                .collect(Collectors.toMap(Account::getAccountId, Account::getInterim_Available_Balance));
        // 6️⃣ 设置到 Usr 对象中（需 Usr 有 balanceEmoneyAcc 字段并加 @Transient）
        for (Usr usr : users) {
            String accid = AccUti.getAccid(AccountSubType.EMoney.name(), usr.getUname());
            BigDecimal bal = unameToBalanceMap.getOrDefault(accid, BigDecimal.ZERO);
            usr.setInterimAvailableBalance(bal);
        }
        return users;

    }

}
