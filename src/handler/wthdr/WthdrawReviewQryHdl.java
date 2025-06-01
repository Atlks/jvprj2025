package handler.wthdr;


import handler.ivstAcc.dto.WthdrawReviewQryDto;
import jakarta.ws.rs.Path;
import util.model.Context;

import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transaction;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTablename;
import static util.algo.ToXX.toSnake;
import static util.tx.Pagging.*;

/**
 * 订单号              会员账号                    标签             VIP等级          上级代理               提现金额           到账金额              审核状态            审核人                    提现时间                         审核时间
 */
@Path("/apiv1/wthdr/WthdrawReviewQryHdl")
public class WthdrawReviewQryHdl  implements RequestHandler<WthdrawReviewQryDto, ApiGatewayResponse> {
    /**

     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(WthdrawReviewQryDto reqdto, Context context) throws Throwable {
        String tablename = getTablename(Transaction.class);
        String fldIdctr=toSnake(Transaction.Fields.creditDebitIndicator);
        var sqlNoOrd = "select * from "+ tablename +" where "
                +fldIdctr+"="+
                toValStr(CreditDebitIndicator.DEBIT.name()) ;

                ;//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
//        if(reqdto.uname!="")
//        {  sqlNoOrd=sqlNoOrd+ " and  uname like "+ encodeSqlAsLikeMatchParam(reqdto.uname);
//            //  sqlprmMap.put("uname",)
//        }

        var sql=sqlNoOrd+" order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));

        System.out.println(sql);

        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession());

        return new ApiGatewayResponse(list1);
    }
}
