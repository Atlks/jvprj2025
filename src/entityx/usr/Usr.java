package entityx.usr;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;

/**
 * @PersistenceCapable 是 DataNucleus（JDO） 中的一个注解，用于标记一个 Java 类是可持久化的（Persistent），也就是说，这个类的对象可以被保存到数据库中。
 *
 * 它是 Java Data Objects（JDO）规范的一部分。JDO 是一种早期的 Java ORM（对象关系映射）标准，类似于 JPA，但现在使用较少，
 */
// C:\Users\attil\.m2\repository\javax\jdo\jdo-api\3.2.1\jdo-api-3.2.1.jar
@Entity
@Table
@org.hibernate.annotations.Comment(  "用户表")
@org.hibernate.annotations.Table(appliesTo = "Usr", comment = "用户表")
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@PersistenceCapable
@Data
public class Usr {
    @NotBlank

    @Valid
    public String uname = "";

    @Comment("邀请人 上级")

    @Column(name = "invtr", columnDefinition = "VARCHAR(255) COMMENT ' 邀请人 上级.'")
    public String invtr="";


    @PrimaryKey
    @Id
    public String id;
    public BigDecimal balance=new BigDecimal(0);// avdBls
    public BigDecimal balanceFreez=new BigDecimal(0);;// avdBls
    public BigDecimal balanceYinliwlt=new BigDecimal(0);;
    public BigDecimal balanceYinliwltFreez=new BigDecimal(0);;

    //保险资金池总额
    public BigDecimal insFdPoolBalance=new BigDecimal(0);;

    //已经充值总额
    public BigDecimal alreadyRechgSum=new BigDecimal(0);;

    //待领取佣金
    public BigDecimal waitApplyCms=new BigDecimal(0);;

    public Usr(String uname) {
        this.uname = uname;
        this.id=uname;
    }

    public BigDecimal getTotalCommssionAmt() {
        if (totalCommssionAmt == null)
            return BigDecimal.valueOf(0);
        return toBigDcmTwoDot(totalCommssionAmt);
    }

    public BigDecimal totalCommssionAmt;

    public Usr(String uname, String pwd) {
        this.uname = uname;

    }

    public Usr() {

    }

    public  boolean enabled=true;

    @CreationTimestamp
    public long crtTimeStmp;

    public long timestamp=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmp;
}
