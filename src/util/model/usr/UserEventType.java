package util.model.usr;

public enum UserEventType {

    // 认证相关
    REGISTER,               // 用户注册
    LOGIN,                  // 登录成功
    LOGOUT,                 // 登出
    LOGIN_FAILED,           // 登录失败
    PASSWORD_RESET,         // 找回密码
    PASSWORD_CHANGED,       // 修改密码
    TWO_FACTOR_ENABLED,     // 启用双因素认证
    TWO_FACTOR_DISABLED,    // 关闭双因素认证

    // 账户信息相关
    PROFILE_UPDATED,        // 更新用户资料
    AVATAR_UPLOADED,        // 上传头像
    EMAIL_BOUND,            // 绑定邮箱
    PHONE_BOUND,            // 绑定手机号
    IDENTITY_VERIFIED,      // 实名认证成功
    ACCOUNT_DELETED,        // 用户注销
    ACCOUNT_FROZEN,         // 用户被封禁
    ACCOUNT_UNFROZEN,       // 解封用户

    // 资金与积分类
    BALANCE_CHANGED,        // 余额变动
    RECHARGE_SUCCESS,       // 充值成功
    WITHDRAW_REQUESTED,     // 发起提现
    WITHDRAW_COMPLETED,     // 提现完成
    POINTS_EARNED,          // 获取积分
    COUPON_RECEIVED,        // 接收优惠券

    // 活动行为
    ACTIVITY_JOINED,        // 参与活动
    DAILY_SIGN_IN,          // 签到
    CONTENT_SHARED,         // 分享内容
    MESSAGE_RECEIVED,       // 接收消息
    MESSAGE_READ,           // 阅读消息


    // 风控与安全
    SUSPICIOUS_LOGIN,       // 异常登录
    BLACKLISTED,            // 加入黑名单
    WHITELISTED,            // 移出黑名单
    PERMISSION_CHANGED,     // 权限变更

    // 平台/系统事件
    WORKFLOW_TRIGGERED,     // 触发工作流
    ACTION_EXECUTED,        // 执行某个系统动作
    CONDITION_MET,          // 满足某个业务条件
    CONDITION_FAILED        // 条件未满足


}

