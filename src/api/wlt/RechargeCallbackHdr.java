package api.wlt;

import com.alibaba.fastjson2.JSONObject;
import entityx.TransDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import service.CmsBiz;
import entityx.ReChgOrd;
import entityx.Usr;
import org.hibernate.Session;
import util.algo.Icall;
import util.excptn.NotExistRow;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static java.time.LocalTime.now;
import static util.log.ColorLogger.*;
import static util.misc.HttpUtil.getFromHttpget;
import static util.tx.HbntUtil.*;
import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.proxy.SprUtil.injectAll4spr;
import static util.misc.util2026.*;

/** 完成充值回调 ivk by
 * 这里不能知识判断sign，很可能去反查以下比较好安全
 * http://localhost:8889/recharge_callback?rechargeId=ordchg2222&transactionId=11&amount=99&status=success&sign=432432
 */
@Path("/recharge_callback")
@Data
@Lazy
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RechargeCallbackHdr implements  Icall<ReChgOrd,Object> {
    public static String saveUrlLogBalance;

    static boolean ovrtTEst = false;

//required=false

    //注解告诉 JSON 序列化库跳过该字段。
  @Lazy
  @Autowired()
 // @Inject("addMoneyToWltService")
 @Qualifier("AddMoneyToWltService")  // 使用类名自动转换
 public Icall addMoneyToWltService1;   //=new AddMoneyToWltService();
    public   String addMoneyToWltService = "AddMoneyToWltService";
//    @Lazy
//   // @Autowired()
//    @Qualifier("addMoneyToYLWltService")
//    public Iservice addMoneyToYLWltService1;

    @Override
    @Tag(name = "wlt")
    @GET
    @Path("/CompleteChargeHdr")
    @QueryParam("id")  //ordid
    //@CookieParam("uname")
    //@CookieValue
    @Transactional
    @RolesAllowed({"", "USER"})  // 只有 ADMIN 和 USER 角色可以访问
    public Object call(@BeanParam ReChgOrd ordDto) throws Throwable {
     //  iniAllField();
     //   injectAll4spr(this);
        ovrtTEst=true;//todo cancel if test ok
        Session session = sessionFactory.getCurrentSession();

        //--------------chk ,qry thrd party api
        JSONObject jo= JSONObject.from(getFromHttpget("xxxxxc?id="+ordDto.id));

        //------------blk chge regch stat=ok
        String mthBiz=colorStr("设置订单状态=完成",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  "+mthBiz);
        ReChgOrd objChrg = findByHerbinate(ReChgOrd.class, ordDto.id, session);
        // System.out.println("\r\n----blk updt chg ord set stat=ok");
        String stat = (String) getField2025(objChrg, "stat", "");
        BigDecimal amt = objChrg.amt;
        if (stat.equals("ok")) {
            System.out.println("alread cpmlt ord,id=" +ordDto. id);
            if (ovrtTEst) {
            } else
                return objChrg;
        }
        if (stat.equals(""))
            objChrg.stat = "ok";
        mergeByHbnt(objChrg, session);




        //----=============add blance n log  ..blk
        String mthBiz2=colorStr("主钱包加钱",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun "+mthBiz2);
        String uname = objChrg.uname;
        TransDto transDto=new TransDto();
        copyProps(objChrg,transDto);
        transDto.refUniqId="reqid="+objChrg.id;
        transDto.lockAccObj=findByHerbinate(Usr.class,ordDto.uname,session);

        addMoneyToWltService1.call(transDto);
        //  System.out.println("\n\r\n---------endblk  kmplt chrg");


        //=====================cals misstn
        String mthBiz23=colorStr("calc cms计算佣金",RED_bright);
        System.out.println("\n\r\n===========⚡⚡bizfun  "+mthBiz23);
        //  System.out.println("\n\r\n===========blk  calcCms4FrmOrdChrg");
        //--------------calc yonjin
        Usr u = new Usr();
        // u.invtr=objU.get("invtr").toString();
        //  calcCms4chrgU(u,amt);
        CmsBiz.calcCms4FrmOrdChrg(objChrg, session);
        // calcCms(uname,amt);
        //  session.getTransaction().commit();
        System.out.println("\n\r\n---------endblk  calcCms4FrmOrdChrg");

        return null;
    }



    private void iniAllField() {


    }


//    public static void main(String[] args) throws Exception {
//        MyCfg.iniCfgFrmCfgfile();
//        ovrtTEst = true;
//        //       drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
//        //  updateOrdChgSetCmplt("ordChrg2025-02-18T21-14-59");
//    }
//    private static void calcCms(String uname, BigDecimal amt) {
//    }

//    public   void updtBlsByAddChrg(ReChgOrd objChrg, Session session) throws Exception {
//        printLn("\n▶️fun updtBlsByAddChrg(", BLUE);
//        printLn("objChrg= " + encodeJson(objChrg), GREEN);
//        System.out.println(")");
//        //  printlnx();
//        //   System.out.println("\r\n ▶fun updtBlsByAddChrg(objChrg= "+encodeJson(objChrg));
//
//        String uname = objChrg.uname;
//        BigDecimal amt = objChrg.getAmt();
//
//
//        Usr objU = findByHbnt(Usr.class, uname, session);
//        if (objU.id == null) {
//            objU.id = uname;
//            objU.uname = uname;
//        }
//
//        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);
//
//        BigDecimal newBls = nowAmt.add(amt);
//        objU.balance = toBigDcmTwoDot(newBls);
//        mergeByHbnt(objU, session);
//
//        //add balanceLog
//        LogBls logBalance = new LogBls();
//        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
//        logBalance.uname = uname;
//        logBalance.changeTime = System.currentTimeMillis();
//        logBalance.changeType = "充值";  //充值增加
//        logBalance.changeMode = "增加";
//        logBalance.changeAmount = amt;
//        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
//        logBalance.newBalance = toBigDcmTwoDot(newBls);
//        System.out.println(" add balanceLog ");
//        persistByHibernate(logBalance, session);
//
//        System.out.println("✅endfun updtBlsByAddChrg()");
//    }
//    private   void updateOrdChgSetCmpltBiz(String id) throws Exception {
//
//        org.hibernate.Session session = sessionFactory.getCurrentSession();
//
//        //------------blk chge regch stat=ok
//        //  om.jdbcurl=saveDirUsrs;
//        //todo start tx
//
//        ReChgOrd objChrg = findByHbnt(ReChgOrd.class, id, session);
//
//
//        System.out.println("\r\n----blk updt chg ord stat=ok");
//        //update chr ord stat
//        //   OrdChrg objChrg = find(id, saveUrlOrdChrg, OrdChrg.class);
//        String stat = (String) getField2025(objChrg, "stat", "");
//        BigDecimal amt = objChrg.amt;
//        if (stat.equals("ok")) {
//            System.out.println("alread cpmlt ord,id=" + id);
//            if (ovrtTEst) {
//
//            } else
//                return;
//        }
//        if (stat.equals(""))
//            objChrg.stat = "ok";
//        mergeByHbnt(objChrg, session);
//        System.out.println("----endblk updt chg ord stat=ok");
//        //  session.merge(objChrg);
//
//        //----add blance n log  ..blk
//        String uname = objChrg.uname;
//        updtBlsByAddChrg(objChrg, session);
//        System.out.println("\n\r\n---------endblk  kmplt chrg");
//
//
//        System.out.println("\n\r\n---------blk  calcCms4FrmOrdChrg");
//        //--------------calc yonjin
//        Usr u = new Usr();
//        // u.invtr=objU.get("invtr").toString();
//        //  calcCms4chrgU(u,amt);
//        CmsBiz.calcCms4FrmOrdChrg(objChrg, session);
//        // calcCms(uname,amt);
//
//        System.out.println("\n\r\n---------endblk  calcCms4FrmOrdChrg");
//    }



}
