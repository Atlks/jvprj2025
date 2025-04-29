package handler.msg;

import cfg.AppConfig;
import entityx.usr.Usr;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.msg.Message;
import model.msg.TlgrmUserx;
import model.msg.ToChat;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
import static cfg.MyCfg.iniContnr;
import static util.algo.CallUtil.lmdIvk;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.*;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

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
        commitTsact();
    }


    private static void bfTst() throws Throwable, SQLException {


        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        openSessionBgnTransact();
        //  iniEvtHdrCtnr();

        //   evtlist4reg.add(new AgtHdl()::regEvtHdl);

    }


}
