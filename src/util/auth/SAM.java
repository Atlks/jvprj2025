package util.auth;


import api.usr.RegDto;
import api.usr.validateRtmExptn;
import entityx.Key;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import util.ex.PwdErrRuntimeExcept;
import util.ex.PwdNotEqExceptn;
import util.tx.findByIdExptn;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncryUtil.Key4pwd4aeskey;
import static util.algo.EncryUtil.encryptAesToStrBase64;
import static util.excptn.ExptUtil.currFunPrms4dbg;
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

        Key pwdstore=new Key();
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

        try {
            currFunPrms4dbg.set(credential);
            UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
            String uname = crdt.getCaller();

            var u = findByHerbinate(Key.class, uname, sessionFactory.getCurrentSession());
            hopePwdEq(u.hashedPassword, SAM.encryPwd(crdt.getPasswordAsString(),u.salt));
            return new CredentialValidationResult(uname, java.util.Set.of("USER"));




        } catch (PwdNotEqExceptn  e) {
            throw new PwdErrRuntimeExcept("PwdErrEx", e);
        } catch (findByIdExptn e) {
            throw new validateRtmExptn(e.getMessage(),e);
        }


    }


}
