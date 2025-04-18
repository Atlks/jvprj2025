package entityx.wlt;

import entityx.baseObj;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table(name = "rechg_ord")
@Data
public class ReChgOrd  {

 //   public String uname;

    //充值金额
    public BigDecimal getAmt() {
        return  toBigDcmTwoDot(amt) ;
    }

    public BigDecimal amt=new BigDecimal(0);
    @NotBlank
    public String uname="";

    //review time
    public long review_timestamp=0;

    public long   timestamp=System.currentTimeMillis();

    @Id
    public String id;

    public String getUname() {
        return uname;
    }

    public String setUname() {
        return uname;
    }

    //审核状态
    public String stat="待审核";   //待审核  ，通过，拒绝

    //审核人
    public String reviewer="";
    public String parentProxy="";

    public String vipLev="";


    //截图附件
    public String img="";

    @CreationTimestamp
    public long crtTimeStmp=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmp=System.currentTimeMillis();

   // public long   timestamp=System.currentTimeMillis();

}
