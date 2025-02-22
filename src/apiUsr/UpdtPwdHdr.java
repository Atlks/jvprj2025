package apiUsr;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import entityx.Usr;
import jakarta.persistence.LockModeType;
import utilBiz.OrmUtilBiz;


import static util.EncodeUtil.encodeMd5;
import static util.HbntUtil.findByHbnt;
import static util.HbntUtil.mergeByHbnt;
import static util.dbutil.addObj;
import static util.util2026.*;

public class UpdtPwdHdr extends BaseHdr {
    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);

        if (uname.equals("")) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }
        String oldpwd = getRequestParameter(exchange, "oldpwd");
        String new_pwd = getRequestParameter(exchange, "pwd");
        //JSONObject jo = getObjDocdb(uname,  saveDirUsrs);
        org.hibernate.Session session =sessionFactory.getCurrentSession()

        Usr u = findByHbnt(Usr.class, uname, LockModeType.PESSIMISTIC_WRITE, session);
//        Usr objU =findByHbnt(Usr.class, lgblsDto.uname, LockModeType.PESSIMISTIC_WRITE,session);
        if (u.pwd.equals(encodeMd5(oldpwd))) {
            // 创建 User 对象
            u.pwd = encodeMd5(new_pwd);
            //   saveDir = saveDir;
            var rzt = mergeByHbnt(u, session);

            wrtResp(exchange, "ok");
        } else {
            wrtResp(exchange, "powNotMatch");
        }
        //blk login ed
//        Object acclog = null;
//        addObj(acclog, "acc", "/db2026/");


    }


}
