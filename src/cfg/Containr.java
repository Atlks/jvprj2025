package cfg;

import model.usr.Usr;
import jakarta.security.enterprise.SecurityContext;
import org.hibernate.SessionFactory;
//ringframework.context.ApplicationEventPublisher;
import service.auth.ISAM;
import service.auth.SAM4chkLgnStatJwtMod;
import service.auth.SAM4reglgn;
// util.algo.ChooseEvtPublshr;
// util.evtdrv.ApplicationEventPublisherImplt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Containr {





   public static List<Consumer<Usr>> evtlist4wthdr=new ArrayList<>();
    public static List<Consumer<Usr>> evtlist4transact=new ArrayList<>();




//    public static ApplicationEventPublisher evtPublisher=new ApplicationEventPublisherImplt();
   // public static EvtPublisherPathevtMod evtPublisherPathmd=new EvtPublisherPathevtMod();
   // public static EvtPublisherObsv evtPublisherObsv=new EvtPublisherObsv();
    public  static  SecurityContext SecurityContext1;
   // public static ChooseEvtPublshr chooseEvtPblshr=new ChooseEvtPublshr();


    public  static  ThreadLocal<Class>  curCtrlCls=new ThreadLocal<>();

    public static String jdbcUrl;
    public static String saveUrlOrdChrg;
    //======ex


    //------------4 auth sam
   // @Qualifier(SAM4regLgn)
    public static ISAM sam4regLgn =new SAM4reglgn();
    public static ISAM sam4chkLgnStat =new SAM4chkLgnStatJwtMod();

    //for tx
    public static SessionFactory sessionFactory;
    public  static  ThreadLocal<Object>  curLockWltAcc=new ThreadLocal<>();
    public static Map cfgMap;
    public static boolean testUnitMode=false;
}
