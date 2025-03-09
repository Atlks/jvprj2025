package util.auth;


import entityx.Pwd;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import util.ex.PwdNotEqExceptn;
import util.tx.findByIdExptn;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncryUtil.Key4pwd4aeskey;
import static util.algo.EncryUtil.encryptAesToStrBase64;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * sam安全授权模块
 */
public class SAM {
    public static String encryPwd(String pwd, Pwd pwdstore) {
   return    encryptAesToStrBase64("p="+pwd+"&slt="+pwdstore.getSalt(), Key4pwd4aeskey);
    }

    public static String encryPwd(String pwd, String salt) {
        return    encryptAesToStrBase64("p="+pwd+"&slt="+ salt, Key4pwd4aeskey);
    }

    public static CredentialValidationResult chkPwd(Credential credential) throws findByIdExptn, PwdNotEqExceptn {

        UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
        String uname = crdt.getCaller();
     //   String uname=credential.;
        var u = findByHerbinate(Pwd.class, uname, sessionFactory.getCurrentSession());
        hopePwdEq(u.hashedPassword, SAM.encryPwd(crdt.getPasswordAsString(),u.salt));
        return new CredentialValidationResult(uname, java.util.Set.of("USER"));

    }
}
