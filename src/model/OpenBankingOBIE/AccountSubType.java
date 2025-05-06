package model.OpenBankingOBIE;


/**
 *ins_fd_pool==
 * 在 OBIE 标准中，最适合的 AccountSubType 是：
 *
 * 选择	原因
 * SavingsAccount	比较合理，保险资金池的本质类似“储蓄账户”，资金集中管理，且流动性相对较低
 * GeneralInvestment	如果资金池用于投资理财、赚收益，可能归到 GeneralInvestment（一般投资账户）
 */
public enum AccountSubType
{

    //EMoney:本金钱包 ，GeneralInvestment 投资账户盈利钱包 ，

    // 电子钱包账户  主账户
    EMoney,
    // 投资账户（ 就是盈利账户 一般证券）
    GeneralInvestment,
    uke_agt,  //代理账户   cms bls

    uke_ins_fd_pool,

    // 活期账户
  //  CurrentAccount,

    // 储蓄账户
    SavingsAccount,

    // 信用卡账户
  //  CreditCard,

    // 记账卡账户
   // ChargeCard,

    // 贷款账户
  //  Loan,

    // 按揭贷款账户
   // Mortgage,

    // 预付卡账户
  //  PrePaidCard,





    // ISA账户（英国免税投资账户）
  //  IndividualSavingsAccount,

    // 定期存款账户
 //   TermDeposit,

    // 租赁账户
 //   Lease,

    // 股票交易账户
 //   ShareTrading,

    // 财务管理账户
    FinancialAccount,

    // 保证金账户（如保证金贷款）
    MarginTrading

    ,usrExt, //扩展统计类的账户
    // 其他（无法归类的账户）
    Other;



}
