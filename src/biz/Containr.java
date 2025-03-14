package biz;

import cfg.EvtPublisherObsv;
import jakarta.security.enterprise.SecurityContext;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import util.algo.ChooseEvtPublshr;
import util.evtdrv.ApplicationEventPublisherImplt;

public class Containr {

    public static ApplicationEventPublisher evtPublisher=new ApplicationEventPublisherImplt();
    public static EvtPublisherPathevtMod evtPublisherPathmd=new EvtPublisherPathevtMod();
    public static EvtPublisherObsv evtPublisherObsv=new EvtPublisherObsv();
    public  static  SecurityContext SecurityContext1;
    public static SessionFactory sessionFactory;
    public static ChooseEvtPublshr chooseEvtPblshr=new ChooseEvtPublshr();
}
