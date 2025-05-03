package handler.usrStt;

import entityx.usr.NonDto;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.Account;
import model.usr.UsrStats;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.List;
import java.util.Map;

import static cfg.Containr.sessionFactory;
// 数据报表》排行榜
public class RankMmbrRchgHdl {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    //  @Override
    public Object handleRequest(NonDto param, Context context) throws Throwable {

        RankRptVo vo = new RankRptVo();
        vo.totalRechg_mbrList = getListTtlDpst();
        vo.totalRechg_agtList = getListTtlDpst4agt();
        vo.totalWthdr_mbrList = qryTtlWthd4mbr();
        vo.totalWthdr_agtList = qryTtlWthd4agt();
        vo.totalProfit_agtList = qryTTlPrft4agt();
        vo.totalProfit_mbrList = qryTtlPrft4mbr();
        vo.totalEchxg_mbrList = qryTtlTrs4mmr();
        vo.totalEchxg_agtList = qryTtlTrs4agt();
        return vo;

        //return null;
    }

    private List<?> qryTtlTrs4agt() {
        var sql = "select * from agent order by  exchangeAmount desc limit 20";
        return qryByHbnt(sessionFactory.getCurrentSession(), sql);
    }

    private List<?> qryTtlTrs4mmr() {

        var sql = "select * from  UsrStats order by totalTransfer desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private List<?> qryTtlPrft4mbr() {
        var sql = "select * from  UsrStats order by totalProfit desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private List<?> qryTTlPrft4agt() {
        var sql = "select * from agent order by  totalCommssionAmt desc limit 20";
        return qryByHbnt(sessionFactory.getCurrentSession(), sql);
    }

    private List<?> qryTtlWthd4agt() {
        var sql = "select * from agent order by  withdrawalAmount desc limit 20";
        return qryByHbnt(sessionFactory.getCurrentSession(), sql);
    }

    private List<?> qryTtlWthd4mbr() {
        var sql = "select * from  UsrStats order by totalWithdraw desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private List<?> getListTtlDpst4agt() {
        var sql = "select * from agent order by  totalRechargeAmount desc limit 20";
        return qryByHbnt(sessionFactory.getCurrentSession(), sql);

    }

    private static List<Map> getListTtlDpst() {
        var sql = "select * from  UsrStats order by totalDeposit desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private static List<Map> qryByHbnt(Session session, String sql) {
        return session.createNativeQuery(sql, Map.class).getResultList();
    }
}
