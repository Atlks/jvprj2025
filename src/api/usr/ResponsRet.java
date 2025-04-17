package api.usr;

import static util.serverless.ApiGateway.httpExchangeCurThrd;

public class ResponsRet
{
    public  String reqUrl;
    public  Object reqParam;
    public  Object ret;

    public  ResponsRet(Object rtObj) {
        reqUrl = String.valueOf(httpExchangeCurThrd.get().getRequestURI());
     //   rt.ret = Collections.singletonMap("tokenJwt", JwtUtil.generateToken(Udto.uname));
  this.ret=rtObj;
    }
    //  public  String token;
}
