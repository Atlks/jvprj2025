package service.auth;


import api.usr.validateRtmExptn;
import entityx.Keyx;
import entityx.SecuryLog;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.hibernate.Session;
import util.ex.PwdErrRuntimeExcept;
import util.ex.PwdNotEqExceptn;
import util.tx.findByIdExptn;

import java.util.Set;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncryUtil.Key4pwd4aeskey;
import static util.algo.EncryUtil.encryptAesToStrBase64;
import static util.excptn.ExptUtil.currFunPrms4dbg;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * sam安全授权模块
 */
public class SAM  implements IdentityStore {
//    public static String encryPwd(String pwd, Pwd pwdstore) {
//   return    encryptAesToStrBase64("p="+pwd+"&slt="+pwdstore.getSalt(), Key4pwd4aeskey);
//    }

    public static String encryPwd(String pwd, String salt) {
        return    encryptAesToStrBase64("p="+pwd+"&slt="+ salt, Key4pwd4aeskey);
    }

    public static void addPwd(String uid,String pwdOri) {

        Keyx pwdstore=new Keyx();
        pwdstore.setUserId(uid);
        pwdstore.hashedPassword = SAM.encryPwd(pwdOri,pwdstore.salt);
        persistByHibernate( pwdstore, sessionFactory.getCurrentSession());
    }

    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {

        System.out.println("fun SAM.vld(crdt=" + encodeJson(credential));
        String uname = null;
        try {
            currFunPrms4dbg.set(credential);
            UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
            uname = crdt.getCaller();


            var u = findByHerbinate(Keyx.class, uname, sessionFactory.getCurrentSession());
            hopePwdEq(u.hashedPassword, SAM.encryPwd(crdt.getPasswordAsString(), u.salt));
            CredentialValidationResult user = new CredentialValidationResult(uname, Set.of("USER"));


            SecuryLog lg = new SecuryLog();
            lg.setOp("vld");
            lg.setUser(uname);
            lg.setUser(uname);
            lg.setDscrp("vld rzt is ok");
            persistByHibernate(lg, sessionFactory.getCurrentSession());
            System.out.println("endfun  SAM.vld().ret=" + encodeJson(user));
            return user;


        } catch (PwdNotEqExceptn e) {
            logEx(e, uname);
            throw new PwdErrRuntimeExcept("PwdErrEx", e);
        } catch (findByIdExptn e) {
            logEx(e,uname);
            throw new validateRtmExptn(e.getMessage(), e);
        }


    }

    private void logEx(Throwable e, String uname) {
        System.out.println("fun logex(u="+uname+",e="+e);
        //must new session not in trx
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
        SecuryLog lg=new SecuryLog();
        lg.setOp("vld");

        lg.setUser(uname);
        lg.setDscrp("vld rzt is err,e="+e.getClass().getName()+",emsg="+e.getMessage());
        persistByHibernate( lg, currentSession);
        currentSession.getTransaction().commit();
    }


}
