package apiUsr;

import com.alibaba.fastjson2.function.impl.ToBigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.math.BigDecimal;

import static apiCms.CmsBiz.toBigDcmTwoDot;

// C:\Users\attil\.m2\repository\javax\jdo\jdo-api\3.2.1\jdo-api-3.2.1.jar
@Entity
@Table
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@PersistenceCapable
public class Usr {
    public String uname="";
    public String invtr;
    public String pwd;

    @PrimaryKey
@Id
    public String id;
    public BigDecimal balance;// avdBls
    public BigDecimal balanceFreez;// avdBls
    public BigDecimal balanceYinliwlt;
    public BigDecimal balanceYinliwltFreez;

    public Usr(String uname) {
  this.uname=uname;
    }

    public BigDecimal getTotalCommssionAmt() {
         if(totalCommssionAmt==null )
                  return BigDecimal.valueOf(0);
        return toBigDcmTwoDot(totalCommssionAmt) ;
    }

    public BigDecimal totalCommssionAmt;

    public Usr(String uname, String pwd) {
   this.uname=uname;
   this.pwd=pwd;
    }

    public Usr() {

    }
}
