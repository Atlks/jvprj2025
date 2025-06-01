package handler.admin;

import handler.admin.dto.EnableDisableSelect;
import handler.admin.dto.IdentityStoreEx;
import handler.admin.dto.restePwdDto;
import handler.dto.ChangePasswordReqDto;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Path;
import model.admin.Admin;
import util.ex.PwdNotEqExceptn;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergex;

public class AdmService implements IdentityStoreEx {

    @PermitAll
    @Path("/apiv1/adm/updtAdmStat")
    public static Object disabUser(EnableDisableSelect dto) throws findByIdExptn_CantFindData {

        var admin = findById(Admin.class, dto.uname(), sessionFactory.getCurrentSession());
        if (dto.enable()==true)
            admin.setEnabled(true);
        if (dto.enable()==false)
            admin.setEnabled(false);
        mergex(admin);
        return admin;
    }

    @Path("/adm/changePwd")
    public static Object chagePwd(ChangePasswordReqDto dto) throws findByIdExptn_CantFindData {

        Admin admin = findById(Admin.class, dto.uname, sessionFactory.getCurrentSession());
        new AdmService().validate(new UsernamePasswordCredential(dto.uname, dto.oldpwd));
        admin.setPasswordEncry(dto.newpwd);
        mergex(admin);
        return admin;
    }

    /**
     * reset pwd adm
     * @param dto
     * @return
     * @throws findByIdExptn_CantFindData
     */
    @PermitAll
    @Path("/adm/resetPwdByAdm")
    public static Object chagePwd(restePwdDto dto) throws findByIdExptn_CantFindData {

        Admin admin = findById(Admin.class, dto.uname, sessionFactory.getCurrentSession());
      //  new AdmService().validate(new UsernamePasswordCredential(dto.uname, dto.oldpwd));
        admin.setPasswordEncry(dto.newpwd);
        mergex(admin);
        return admin;
    }


    @Override
    public CredentialValidationResult validate(Credential credential) throws ValidationException {
        Admin admin = null;
        UsernamePasswordCredential credential1;
        credential1 = (UsernamePasswordCredential) credential;
        try {

            admin = findById(Admin.class, credential1.getCaller(), sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            throw new ValidationException(e);
        }
        try {
            hopePwdEq(admin.getPassword(), encodeMd5(credential1.getPasswordAsString()));
        } catch (PwdNotEqExceptn e) {
            throw new ValidationException(e);
        }
        return  new CredentialValidationResult(CredentialValidationResult.Status.VALID.name()) ;
    }
}
