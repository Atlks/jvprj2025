package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;

// C:\Users\attil\.m2\repository\javax\jdo\jdo-api\3.2.1\jdo-api-3.2.1.jar
@Entity
@Table
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@PersistenceCapable
@Data
public class Usr {
    @NotBlank
    @NotNull
    @Valid
    public String uname = "";
    public String invtr="";
    public String pwd="";

    @PrimaryKey
    @Id
    public String id;
    public BigDecimal balance=new BigDecimal(0);// avdBls
    public BigDecimal balanceFreez=new BigDecimal(0);;// avdBls
    public BigDecimal balanceYinliwlt=new BigDecimal(0);;
    public BigDecimal balanceYinliwltFreez=new BigDecimal(0);;

    public Usr(String uname) {
        this.uname = uname;
    }

    public BigDecimal getTotalCommssionAmt() {
        if (totalCommssionAmt == null)
            return BigDecimal.valueOf(0);
        return toBigDcmTwoDot(totalCommssionAmt);
    }

    public BigDecimal totalCommssionAmt;

    public Usr(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    public Usr() {

    }

    @CreationTimestamp
    public long crtTimeStmp;

    @UpdateTimestamp
    public long updtTmstmp;
}
