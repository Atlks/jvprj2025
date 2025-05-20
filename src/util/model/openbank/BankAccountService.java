package util.model.openbank;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Transaction;
import util.model.CrudRzt;

import java.math.BigDecimal;
import java.util.List;

/**
 * 银行账户相关的服务接口
 *
 * 对于“银行账户（Bank Account）”，常见操作会比普通账户更复杂一些，通常涵盖：
 *
 * 账户开立、查询、冻结、销户
 *
 * 查询余额、交易明细
 *
 * 转账、充值、提现
 *
 * 设置或验证支付密码
 *
 * ✅ 常见银行账户操作清单
 * 操作	描述
 * openAccount	开立新银行账户
 * closeAccount	注销银行账户
 * getAccountById	查询账户信息（如余额、状态）
 * deposit	充值（例如现金入账、第三方转入）
 * withdraw	提现（例如转出到银行卡）
 * transfer	转账到另一个账户
 * freezeAccount	冻结账户（涉嫌风险、法院冻结等）
 * unfreezeAccount	解冻账户
 * listTransactions	获取交易流水（分页）
 * changePaymentPassword	修改支付密码
 * verifyPaymentPassword	验证支付密码
 */
public interface BankAccountService {

    /**
     * adjustBalanceDecrease
     * 场景	建议名称	含义或使用说明
     * 增加余额（奖励、修正）	adjustBalanceIncrease	表示后台人工增加余额（非充值）
     * 减少余额（纠错、惩罚）	adjustBalanceDecrease	表示后台人工扣除余额（非提现）
     * 通用调整（正负都可）	adjustBalance	通过参数指定增减方向
     * 更高级通用名称	adminAdjustBalance	明确表示是管理员操作
     * 业务通用命名（系统发起）	systemAdjustment	如果不仅管理员，也包括系统自动操作
     */
    /**
     * 开立账户
     */
    CrudRzt openAccount(Account account);

    /**
     * 注销账户
     */
    CrudRzt closeAccount(String accountId);

    /**
     * 查询账户信息
     */
    Account getAccountById(String accountId);

    /**
     * 存款（充值）
     */
    CrudRzt deposit(String accountId, BigDecimal amount);

    /**
     * 提现
     */
    /**
     * 提现
     */
    CrudRzt withdraw(String accountId, BigDecimal amount);

                     /**
                      * 转账到另一个账户
                      */
    CrudRzt transfer(String fromAccountId, String toAccountId, BigDecimal amount);

    /**
     * 冻结账户
     */
    CrudRzt freezeAccount(String accountId);

    /**
     * 解冻账户
     */
    CrudRzt unfreezeAccount(String accountId);

    /**
     * 查询交易流水
     */
    List<Transaction> listTransactions(String accountId, int page, int pageSize);

    /**
     * 修改支付密码
     */
    CrudRzt changePaymentPassword(String accountId, String oldPwd, String newPwd);

    /**
     * 验证支付密码
     */
    boolean verifyPaymentPassword(String accountId, String inputPwd);
}