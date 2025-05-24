package handler.msg;

import cfg.MainStart;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

import static cfg.Containr.sessionFactory;
// static cfg.IniCfg.iniContnr;
import static cfg.MainStart.iniContnr;
import static util.algo.CallUtil.lmdIvk;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.*;
import static util.tx.TransactMng.commitx;
import static util.tx.TransactMng.beginx;

@Path("/msg/RcvHdl")

@PermitAll

@NoArgsConstructor
@Data
public class RcvHdl {

    public Object handleRequest(MsgRqDto reqdto) throws Throwable {
        Session session = sessionFactory.getCurrentSession();


        List li = (getResultList(
                "select * from Message " +
                        " where toChatTgtId=" + encodeSqlPrmAsStr(reqdto.toChatTargetId) +
                        " order by date desc", session));

        return li;
    }



    public static void main(String[] args) throws Throwable {
        bfTst();
        Session session = sessionFactory.getCurrentSession();

        MsgRqDto dto = new MsgRqDto();
        dto.uid = "666";
        dto.toChatTargetId = ("777");

          List li= lmdIvk(RcvHdl.class, dto);
        System.out.println(encodeJson(li));
        commitx();
    }


    private static void bfTst() throws Throwable, SQLException {


        new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        beginx();
        //  iniEvtHdrCtnr();

        //   evtlist4reg.add(new AgtHdl()::regEvtHdl);

    }


}
