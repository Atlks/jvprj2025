package orgx.msc;

public enum AdminServiceType {
    ADMIN_USER_SERVICE,       // 管理员账户管理（创建、权限分配）
    ROLE_MANAGEMENT_SERVICE,  // 角色权限管理（RBAC）
    AUDIT_LOG_SERVICE,        // 操作日志审计（记录管理员行为）
    REPORT_GENERATION_SERVICE,// 统计报表（用户数据、财务数据）
    SYSTEM_CONFIG_SERVICE,    // 系统配置管理（更新全局设置）
    NOTIFICATION_SERVICE,     // 管理员通知（邮件、短信）
    TASK_SCHEDULING_SERVICE,  // 定时任务（自动化运营）
    SECURITY_SERVICE,         // 安全管理（账号封禁、异常检测）
    DATABASE_MAINTENANCE_SERVICE, // 数据库维护（清理、备份）
    CONTENT_MODERATION_SERVICE // 内容审核（屏蔽违规内容）
}
