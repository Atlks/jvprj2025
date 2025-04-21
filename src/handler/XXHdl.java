package handler;

import handler.agt.QryAgtsDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Context;
import util.algo.FunFaas;
import util.serverless.ApiGatewayResponse;


//@path is auto
@PermitAll
public class XXHdl {


    /**
     * azure fmt
     * 还是需要返回apires的，因为可能需要包含errcode
     * 但是好像可以返回err with errcode just ok...
     * //todo dft return obj jsonfmt...
     * //all ret json
     * name is        obj  run(dto) is ok...
     * @param reqdto
     * @return
     */
    public Object run(QryAgtsDto reqdto)
    {
        System.out.println("....");
        return  new ApiGatewayResponse(0);
    }



}
