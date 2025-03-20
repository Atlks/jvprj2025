package biz;

import cfg.EvtPublisherObsv;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import service.auth.ISAM;
import service.auth.SAM4chkLgnStatJwtMod;
import service.auth.SAM4reglgn;
import util.algo.ChooseEvtPublshr;
import util.evtdrv.ApplicationEventPublisherImplt;

public class Containr {

    public static ApplicationEventPublisher evtPublisher=new ApplicationEventPublisherImplt();
    public static EvtPublisherPathevtMod evtPublisherPathmd=new EvtPublisherPathevtMod();
    public static EvtPublisherObsv evtPublisherObsv=new EvtPublisherObsv();
    public  static  SecurityContext SecurityContext1;
    public static ChooseEvtPublshr chooseEvtPblshr=new ChooseEvtPublshr();


    //======ex


    //------------4 auth sam
   // @Qualifier(SAM4regLgn)
    public static ISAM sam4regLgn =new SAM4reglgn();
    public static ISAM sam4chkLgnStat =new SAM4chkLgnStatJwtMod();

    //for tx
    public static SessionFactory sessionFactory;
    public  static  ThreadLocal<Object>  curLockWltAcc=new ThreadLocal<>();
}
