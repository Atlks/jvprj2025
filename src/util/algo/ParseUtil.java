package util.algo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ParseUtil {


    public static @NotNull  Map<String, String> parseUrl(@org.hibernate.validator.constraints.NotBlank @NotBlank  String url) {
        Map<String, String> result = new HashMap<>();
        try {
            URI uri = new URI(url);

            if (uri.getScheme() != null) result.put("scheme", uri.getScheme());
            if (uri.getUserInfo() != null) {
                String[] userInfo = uri.getUserInfo().split(":");
                result.put("user", userInfo[0]);
                if (userInfo.length > 1) result.put("pass", userInfo[1]);
            }
            if (uri.getHost() != null) result.put("host", uri.getHost());
            if (uri.getPort() != -1) result.put("port", String.valueOf(uri.getPort()));
            if (uri.getPath() != null) result.put("path", uri.getPath());
            if (uri.getQuery() != null) result.put("query", uri.getQuery());
            if (uri.getFragment() != null) result.put("fragment", uri.getFragment());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }
}
