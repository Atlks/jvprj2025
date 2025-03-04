

=loign only  implt  IdentityStore is ok


=expml loginControler

public Object call(@BeanParam Usr usr_dto) throws Exception, PwdErrEx {
 
        validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));


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
            var u = findByHerbinate(Usr.class, uname, sessionFactory.getCurrentSession());
            hopePwdEq(u.pwd,  encryptAesToStrBase64(crdt.getPasswordAsString(), Key4pwd4aeskey));
            return new CredentialValidationResult(uname, java.util.Set.of("USER"));

        } catch (NotExistRow e) {
            throw new UserNotExistRuntimeExcept("用户不存在", e);
        } catch (PwdNotEqExceptn e) {
            throw new PwdErrRuntimeExcept("PwdErrEx", e);
        }


    }


= web api proxy chklgn stat

    private void urlAuthChkV2(HttpExchange exchange) throws ValideTokenFailEx, AuthenticationException {

        injectAll4spr(this);
        Class<?> aClass = this.getClass();
        if (aClass == AtProxy4api.class) {
            aClass = this.target.getClass();
        }
        if (needLoginUserAuth(aClass)) {
             new ChkLgnStatAuthenticationMechanism().validateRequest(null, null, null);
        }
    }





public class chkLgnStatAuthMchsmJwtMod implements HttpAuthenticationMechanism {
/**
* 登录验证HttpAuthenticationMechanism 接口
* 步骤，拿到用户名密码frm http or dto，检测validate
*
* @return
* @throws AuthenticationException
*/
@Override
public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {


        try {
            @NotNull
            String uname = getUsernameFrmJwtToken(httpExchangeCurThrd.get());
            new ChkLgnStatAuthenticationMechanism().validate(new UsernamePasswordCredential(uname, "noNeed"));
            return AuthenticationStatus.SUCCESS;
        } catch (Throwable e) {
            e.printStackTrace();
            appendEx2lastExs(e);
            throw new AuthenticationException("" + e.getMessage(), e);
        }


    }



    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     * @return
     */
    @Override
    public CredentialValidationResult validate(@NotNull Credential credential2) {


        try {
            UsernamePasswordCredential credential = (UsernamePasswordCredential) credential2;
            // 示例身份验证逻辑
            @NotNull
            String caller = credential.getCaller();
            chkCantBeEmpty("VarName.uname",caller);
            return new CredentialValidationResult(caller, java.util.Set.of("USER"));
        } catch (IsEmptyEx e) {
            throw new AuthenticationExceptionRuntimeException(e);
        }

    }