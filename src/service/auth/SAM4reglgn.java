package service.auth;


import annos.Observes;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import util.evtdrv.AnotherEvent;
import util.misc.AuthenticationExceptionRtm;
import util.tx.findByIdExptn_CantFindData;
import util.validateRtmExptn;
import entityx.usr.Keyx;
import entityx.usr.SAMSecuryLog;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import org.hibernate.Session;
import util.ex.PwdErrRuntimeExcept;
import util.ex.PwdNotEqExceptn;

import java.util.Set;

import static api.usr.LgnHdr3.loginVldObsvs;
import static cfg.AppConfig.sessionFactory;
import static util.algo.EncryUtil.Key4pwd4aeskey;
import static util.algo.EncryUtil.encryptAesToStrBase64;

import static util.excptn.ExptUtil.currFunPrms4dbg;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.*;

@Service
/**
 * sam安全授权模块   stoer in db,,this sam for reg login
 */
public class SAM4reglgn implements ISAM {
//    public static String encryPwd(String pwd, Pwd pwdstore) {
//   return    encryptAesToStrBase64("p="+pwd+"&slt="+pwdstore.getSalt(), Key4pwd4aeskey);
//    }

    @EventListener({LoginValidEvt.class, AnotherEvent.class})
    public CredentialValidationResult validate(LoginValidEvt evt) {
        Credential crdt = (Credential) evt.getSource();
        return validate(crdt);
    }

    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     *
     * @param credential
     * @return
     */
    @Override
    @Observes({loginVldObsvs})
    @EvtLsnr({"lgnVldEvt"})
    public CredentialValidationResult validate(Credential credential) {

        System.out.println("\n\n==============================sam vld===");
        System.out.println("fun SAM.vld(crdt=" + encodeJson(credential));
        String uname = null;
        try {
            currFunPrms4dbg.set(credential);
            UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
            uname = crdt.getCaller();


            var k = findByHerbinate(Keyx.class, uname, sessionFactory.getCurrentSession());


            if( k.frz)
                throw  new AuthenticationExceptionRtm(" acc frz ");
            String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;
            hopePwdEq(k.hashedPassword, geneKey(data));
            CredentialValidationResult user = new CredentialValidationResult(uname, Set.of("USER"));


            // addLogVldSucess()
            addLogVldSucess(uname);
            System.out.println("endfun  SAM.vld().ret=" + encodeJson(user));
            return user;


        } catch (PwdNotEqExceptn e) {
            addLogVldFail(uname, e);
            throw new PwdErrRuntimeExcept("PwdErrEx", e);
        } catch (findByIdExptn_CantFindData e) {
            addLogVldFail(uname, e);
            throw new validateRtmExptn(e.getMessage(), e);
        }


    }

    public String geneKey(String oriData) {


        return encryptAesToStrBase64(oriData, Key4pwd4aeskey);
    }

    public void storeKey(String uid, String oriKey) {

        Keyx pwdstore = new Keyx();
        pwdstore.setUserId(uid);
        String data = "p=" + oriKey + "&slt=" + pwdstore.salt;
        pwdstore.hashedPassword = geneKey(data);
        mergeByHbnt(pwdstore, sessionFactory.getCurrentSession());
        //here updt or isnert,bcs rest pwd is updt...reg is add
    }

    public void addLogVldSucess(String uname) {
        SAMSecuryLog lg = new SAMSecuryLog();
        lg.setOp("vld");
        lg.setUser(uname);
        lg.setDscrp("vld rzt is ok");
        persistByHibernate(lg, sessionFactory.getCurrentSession());
    }


    public void addLogVldFail(String uname, Throwable e) {
        System.out.println("fun logex(u=" + uname + ",e=" + e);
        //must new session not in trx
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
        SAMSecuryLog lg = new SAMSecuryLog();
        lg.setOp("vld");

        lg.setUser(uname);
        lg.setDscrp("vld rzt is err,e=" + e.getClass().getName() + ",emsg=" + e.getMessage());
        persistByHibernate(lg, currentSession);
        currentSession.getTransaction().commit();
    }
}
