package model.acc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 表示一个以太坊地址的各类余额信息
 */
public class EthereumBalance {

    /** 钱包地址 */
    private String address;

    /** ETH 原生余额（单位 ETH） */
    private BigDecimal ethBalance;

    /** ERC-20 Token 余额（key: 合约地址或简称，value: 数量） */
    private Map<String, BigDecimal> erc20Balances = new HashMap<>();

    /** 已授权额度（key: 合约地址，value: 数量） */
    private Map<String, BigDecimal> approvedAllowances = new HashMap<>();

    /** 已质押余额（例如 ETH2 Staking） */
    private BigDecimal stakedBalance = BigDecimal.ZERO;

    /** 可领取奖励（来自质押或流动性质押协议） */
    private BigDecimal pendingRewards = BigDecimal.ZERO;

    /** 锁仓余额（未解锁或合约冻结部分） */
    private BigDecimal lockedBalance = BigDecimal.ZERO;

    /** Layer 2 网络的余额（key: L2 名称，如 zkSync、Arbitrum） */
    private Map<String, BigDecimal> layer2Balances = new HashMap<>();

    // ==== Getter & Setter 省略，可用 Lombok 注解简化 ====

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getEthBalance() {
        return ethBalance;
    }

    public void setEthBalance(BigDecimal ethBalance) {
        this.ethBalance = ethBalance;
    }

    public Map<String, BigDecimal> getErc20Balances() {
        return erc20Balances;
    }

    public void setErc20Balances(Map<String, BigDecimal> erc20Balances) {
        this.erc20Balances = erc20Balances;
    }

    public Map<String, BigDecimal> getApprovedAllowances() {
        return approvedAllowances;
    }

    public void setApprovedAllowances(Map<String, BigDecimal> approvedAllowances) {
        this.approvedAllowances = approvedAllowances;
    }

    public BigDecimal getStakedBalance() {
        return stakedBalance;
    }

    public void setStakedBalance(BigDecimal stakedBalance) {
        this.stakedBalance = stakedBalance;
    }

    public BigDecimal getPendingRewards() {
        return pendingRewards;
    }

    public void setPendingRewards(BigDecimal pendingRewards) {
        this.pendingRewards = pendingRewards;
    }

    public BigDecimal getLockedBalance() {
        return lockedBalance;
    }

    public void setLockedBalance(BigDecimal lockedBalance) {
        this.lockedBalance = lockedBalance;
    }

    public Map<String, BigDecimal> getLayer2Balances() {
        return layer2Balances;
    }

    public void setLayer2Balances(Map<String, BigDecimal> layer2Balances) {
        this.layer2Balances = layer2Balances;
    }
}
