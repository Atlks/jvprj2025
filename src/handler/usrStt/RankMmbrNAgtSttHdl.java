package handler.usrStt;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import util.model.Context;
import model.usr.UsrExtAmtStats;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

import static cfg.Containr.sessionFactory;
import static util.algo.GetUti.getTableName;

// 数据报表》排行榜
//   /usrStt/RankMmbrNAgtSttHdl
@PermitAll
public class RankMmbrNAgtSttHdl {
    /**
     * @param param

     * @return
     * @throws Throwable
     */
    //  @Override
    public Object handleRequest(NonDto param ) throws Throwable {

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

        //return null ;
    }

    private List<?> qryTtlTrs4agt() {
        var sql = "select * from agent order by  exchangeAmount desc limit 20";
        return qryByHbnt(sessionFactory.getCurrentSession(), sql);
    }
   public static String usrStatsTbl = getTableName(UsrExtAmtStats.class);
    private List<?> qryTtlTrs4mmr() {


        var sql = "select * from  " + usrStatsTbl + " order by totalTransfer desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private List<?> qryTtlPrft4mbr() {
        var sql = "select * from  " + usrStatsTbl + " order by totalProfit desc limit 20";
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
        String usrStats = "UsrStats";
        var sql = "select * from  " + usrStatsTbl + " order by totalWithdraw desc limit 20";
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
        String usrStats = "UsrStats";
        var sql = "select * from  " + usrStatsTbl + " order by totalDeposit desc limit 20";
        //  Query<?> query = ;
        // query.setParameter("amount", 1);
        Session session = sessionFactory.getCurrentSession();
        return qryByHbnt(session, sql);
    }

    private static List<Map> qryByHbnt(Session session, String sql) {
        return session.createNativeQuery(sql, Map.class).getResultList();
    }
}
