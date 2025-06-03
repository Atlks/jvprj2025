package orgx.uti.http;

import lombok.Data;

@Data
public class ApiRsps {
    private final Exception e;
    private final String errcode;
    private final String errmsg;
    public  String name="apiResps";
    public ApiRsps(Exception e) {
        this.e=e;
        this.errcode=e.getClass().getName();
        this.errmsg=e.getMessage();
    }
}
