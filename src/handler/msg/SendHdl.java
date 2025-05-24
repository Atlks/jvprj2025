package handler.msg;

import cfg.MainStart;
import model.usr.Usr;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.msg.Message;
import model.msg.TlgrmUserx;
import model.msg.ToChat;
import org.hibernate.Session;

import java.sql.SQLException;

import static cfg.Containr.sessionFactory;
// static cfg.IniCfg.iniContnr;
import static cfg.MainStart.iniContnr;
import static util.algo.CallUtil.lmdIvk;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.persist;
import static util.tx.TransactMng.*;

@Path("/msg/send")

@PermitAll

@NoArgsConstructor
@Data
public class SendHdl {

    public Object handleRequest(MsgRqDto reqdto) throws Throwable {
        Session session = sessionFactory.getCurrentSession();
        Message msg = new Message();

        Usr fromU = findById(Usr.class, reqdto.uid, session);
        Usr toU= findById(Usr.class, reqdto.toChatTargetId, session);
        ToChat to = toChatTgt(toU);
        msg.setFrom(toTgUser(fromU));
        msg.setFromUid(reqdto.uid);

        msg.setChat(to);
        msg.setToChatTgtId(reqdto.toChatTargetId);


        msg.setText(reqdto.text);

        persist(msg, session);
        return "ok";
    }

    private ToChat toChatTgt(Usr fromU) {
        ToChat to=new ToChat();
        to.setId(fromU.id);
        to.setUsername(fromU.id);
        return  to;
    }

    private TlgrmUserx toTgUser(Usr fromU) {
        TlgrmUserx tu=new TlgrmUserx();
        tu.setId(fromU.id);
        tu.setUsername(fromU.uname);
        return  tu;

    }


    public static void main(String[] args) throws Throwable {
        bfTst();
        Session session = sessionFactory.getCurrentSession();

        MsgRqDto dto=new MsgRqDto();
        dto.uid="666";
        dto.toChatTargetId=("777");
        dto.text="nihao";
        lmdIvk(SendHdl.class,dto);

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
