
在 Java JSR（Java Specification Requests） 规范中，没有一个单独的 JSR 专门规定如何存储和读取当前用户名和权限，但多个 JSR 涉及 身份验证、授权和安全管理，其中包括：

相关 JSR 规范
JSR 375: Java EE Security API

提供标准化的 身份验证和授权 机制。

允许使用 SecurityContext 获取当前用户：

java
SecurityContext securityContext = SecurityContext.getCurrent();
String username = securityContext.getCallerPrincipal().getName();
适用于 Jakarta EE / Java EE 应用。


JSR 115: Java Authorization Contract for Containers

定义了 基于角色的访问控制（RBAC） 机制。

允许使用 isUserInRole() 检查权限：

java
boolean isAdmin = request.isUserInRole("ADMIN");
✅ 使用 isUserInRole() 检查用户角色



Spring Security（非 JSR，但广泛使用）

提供 SecurityContextHolder 获取当前用户：

java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName();