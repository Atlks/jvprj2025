package util.model;

//users,,,roles,user_roles,transactions
// 要做一个类似于交易金融业务系统，里面包含用户 管理员 用户钱包，资金收益账户  交易流水 多级代理与返佣
public class TableNames {

    public static final String UsrExtAmtStats = "u_ext_amt_stats";

    //sys mdel
    public static String cfg = "cfg";
    public static String users = "users";
    public static String admins = "admins";
    public static String roles = "roles";
    public static String admin_roles = "user_roles";


    //user rpt
    //usr_ext_amt_rpt
    //usr_ext amt month rpt



    //三、交易与流水模块
    public static String accounts="accounts";
    public static String transactions="transactions";
    public static String settlements; //	清算记录（用于批量日终结算）


    //agt sys
    public static String agents="agents";
    public static String agent_relations;
    //多级代理关系表（如 parent_id 指向上级代理）


    //cms sys.... trx_comm
    public static String commissions; //返佣记录（记录每笔返佣来源、金额、下级ID）
    public static String commission_rules;//	返佣规则配置（如级数、比例等）


    //

    //tx_rechage  wdhtr

//    五、充值与提现
//    表名	说明
//    recharges	充值记录表
//    withdrawals	提现记录表
//    payment_channels	支付渠道信息（银行卡、USDT、支付宝等）
//}



  //  六、系统与审计日志（可选）
  //  表名	说明
  public static String    login_logs ;//	登录日志
    public static String    operation_logs	;//操作日志（管理员操作记录）
    public static String    configurations	;//系统配置项（返佣比例、手续费等）



//    七、消息与通知（可选）
//    表名	说明
//    messages	系统消息或通知（如返佣到账通知）
//    message_logs	用户已读/未读状态
}
