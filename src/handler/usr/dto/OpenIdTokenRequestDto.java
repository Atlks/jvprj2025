package handler.usr.dto;

public class OpenIdTokenRequestDto {

    /**
     * OpenID Connect /token 接口常用请求方式
     *
     * Grant Type	含义	使用场景
     * authorization_code	授权码模式	标准网页登录后跳转，安全性高（支持 OIDC）
     * refresh_token	使用刷新令牌获取新 access_token	用于长时间有效 session 的 token 更新
     * client_credentials	客户端模式	服务端与服务端通信（不涉及用户）
     * password（已废弃）	资源所有者密码模式	用户名密码直接交换 token（不推荐，已不再支持）
     */

    private String grant_type = "client_credentials";
    private String code;
    private String redirect_uri;
    private String client_id;
    private String client_secret;

    // Getters and Setters
    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
