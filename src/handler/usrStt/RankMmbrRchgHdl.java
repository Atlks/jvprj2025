package handler.usrStt;

import entityx.usr.NonDto;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.Account;
import model.usr.UsrStats;
import org.hibernate.query.Query;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.Map;

import static cfg.Containr.sessionFactory;

public class RankMmbrRchgHdl {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
  //  @Override
    public Object handleRequest(NonDto param, Context context) throws Throwable {

       var sql="select * from  UsrStats order by totalDeposit desc limit 20";
      //  Query<?> query = ;
       // query.setParameter("amount", 1);
        return sessionFactory.getCurrentSession().createNativeQuery(sql, Map.class).getResultList();

        //return null;
    }
}
