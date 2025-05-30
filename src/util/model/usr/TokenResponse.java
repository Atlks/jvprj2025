package util.model.usr;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String token_type = "Bearer";
    private int expires_in;
    private String refresh_token;
    //想要访问用户的哪些信息或功能权限。，用于 声明客户端申请访问用户资源的权限范围
    private String scope = "openid profile email";
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

}

