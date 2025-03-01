package cfg;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationService {

    public boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        String userId = getUserIdFromCookie(request);
        if (userId != null) {
            // 在这里验证用户标识是否有效
            if (isValidUserId(userId)) {
                return true;
            } else {
                // 如果用户标识无效，则删除 Cookie
                deleteUserIdCookie(response);
            }
        }
        return false;
    }

    private boolean isValidUserId(String userId) {
        // 在这里实现用户标识验证逻辑，例如查询数据库
        // ...
        return true; // 示例：始终返回 true
    }

    public void login(HttpServletResponse response, String userId) {
        setUserIdCookie(response, userId);
    }

    public void logout(HttpServletResponse response) {
        deleteUserIdCookie(response);
    }

    private String getUserIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void setUserIdCookie(HttpServletResponse response, String userId) {
        Cookie cookie = new Cookie("userId", userId);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 设置 Cookie 过期时间为 7 天
        cookie.setHttpOnly(true); // 禁用 JavaScript 访问 Cookie
        cookie.setSecure(true); // 仅通过 HTTPS 发送 Cookie
        response.addCookie(cookie);
    }

    private void deleteUserIdCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", "");
        cookie.setMaxAge(0); // 设置 Cookie 过期时间为 0，立即删除
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
