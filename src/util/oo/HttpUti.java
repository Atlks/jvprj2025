package util.oo;

import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotBlank;

import static org.apache.commons.lang3.StringUtils.trim;

public class HttpUti {

    //  从http请求头部提取    AuthChkMode  标头  dft jwt
    public static @NotBlank String getAuthChkMode(HttpExchange he) {
// 从请求头中获取 "AuthChkMode" 标头
        String authChkMode = he.getRequestHeaders().getFirst("AuthChkMode");
        if (authChkMode == null)
            return "jwt";
        return trim(authChkMode);

    }
}
