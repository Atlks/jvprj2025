package handler.wthdr;


import handler.ylwlt.dto.WthdrawReviewQryDto;
import jakarta.ws.rs.core.Context;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.tx.Pagging.*;

/**
 * 订单号              会员账号                    标签             VIP等级          上级代理               提现金额           到账金额              审核状态            审核人                    提现时间                         审核时间
 */
public class WthdrawReviewQryHdl  implements RequestHandler<WthdrawReviewQryDto, ApiGatewayResponse> {
    /**

     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(WthdrawReviewQryDto reqdto, Context context) throws Throwable {
        var sqlNoOrd = "select * from Transactions where creditDebitIndicator='debit' ";//for count    where  uname =:uname
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
