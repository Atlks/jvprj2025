package model.auth;


/**
 * 角色名	权限描述
 * 超级管理员 (Super Admin)	拥有系统所有权限，包括用户、权限、配置、数据清理等
 * 管理员 (Admin)	管理部分模块（如用户管理、订单管理），但权限不及超级管理员
 * 运营人员 (Operator)	负责内容发布、活动配置、推送管理、数据查看
 * 客服人员 (Customer Support)	查看用户信息、处理用户反馈和投诉、操作部分数据
 * 财务人员 (Finance)	查看并导出财务相关数据，如订单、账单、退款、提现
 * 审核人员 (Auditor/Reviewer)	审核内容、用户、资质、交易等
 * 内容编辑 (Editor)	发布、编辑文章、公告、产品等内容
 * 开发/技术支持 (DevOps/IT)	查看系统状态，可能有特殊技术权限，如日志查看等
 */
public enum Role {
   tourist, USER, ADMIN ,Operator,Finance,Auditor,Editor
}

