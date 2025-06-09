package handler.usr;

import api.usr.AnswerErr;
import entityx.usr.SecurityQuestion;
import entityx.usr.SetWithdrawalPasswordDto;
import handler.usr.dto.CantFindQstn;
import handler.usr.dto.ResetWithdrawPwdRequestDto;
import jakarta.ws.rs.Path;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findById;

public class RstWthdrPwdHdr {
    @Path("/apiv1/user/resetWithdrawPwd")

    public Object resetWithdrawPwd(ResetWithdrawPwdRequestDto reqdto) throws Throwable {

        // 1. 参数校验
//        if (uname == null || uname.isBlank()) {
//            return "{\"code\": \"param_error\", \"msg\": \"用户名不能为空\"}";
//        }
//        if (answer == null || answer.isBlank()) {
//            return "{\"code\": \"param_error\", \"msg\": \"安全问题答案不能为空\"}";
//        }
//        if (newWithdrawPwd == null || newWithdrawPwd.length() < 6) {
//            return "{\"code\": \"param_error\", \"msg\": \"提现密码格式不正确\"}";
//        }

        // 2. 查询用户


        // 3. 验证安全问题答案（假设用户表里有 answer 字段）
        SecurityQuestion sq;
        try{
            sq=    findById(SecurityQuestion.class,reqdto.getUname(),sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            throw new CantFindQstn("没有设置安全问题");
        }

        if(! (reqdto.getAnswer()) .equals(sq.answer))
            throw  new AnswerErr("");

//        if(!reqdto.getNewPassword().equals(reqdto.getConfirmPassword()))
//            throw  new PwdNotEqExceptn("");

//        Usr u=findByHerbinate(Usr.class,reqdto.uname,sessionFactory.getCurrentSession());
      //  sam4regLgn.storeKey(reqdto.getAnswer(), reqdto.getNewPassword());


        // 4. 设置新密码（假设有 setWithdrawPwd 方法，加密存储）
        SetWithdrawalPasswordDto setdto=new SetWithdrawalPasswordDto();
        setdto.setUname(reqdto.getUname());
        setdto.setPwd(reqdto.getNewWithdrawPwd());
     return    new SetWthdrPwdHdr().handleRequest(setdto,null);

        // 5. 保存用户信息（例如使用 Hibernate / MyBatis）



    }

}
