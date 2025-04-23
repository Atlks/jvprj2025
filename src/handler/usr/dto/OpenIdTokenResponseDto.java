package handler.usr.dto;

public class OpenIdTokenResponseDto {
    /**
     * OAuth2 访问令牌，用于访问受保护资源。
     */
    private String access_token;

    /**
     * 令牌类型，通常为 "Bearer"
     */
    private String token_type="Bearer";

    /**
     * access_token 的有效时间（单位秒）
     */
    private int expires_in=3600*24*100;

    /**
     * 可选字段，用于刷新 access_token
     */
    private String refresh_token;

    /**
     * OpenID Connect 的身份令牌，JWT 格式
     */
    private String id_token;

    // Getters and Setters
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
