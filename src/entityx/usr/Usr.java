package entityx.usr;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.*;

//import javax.jdo.annotations.PersistenceCapable;
//import javax.jdo.annotations.PrimaryKey;
import java.math.BigDecimal;
import java.time.Instant;

import static service.CmsBiz.toBigDcmTwoDot;

/**
 *   Usr实体
 * . OpenID Connect / OAuth2 的 UserInfo 标准fg
 * OpenID Connect 规定了一组用户字段，用于 OAuth 登录完成后的 userinfo 接口返回：
 *
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
//@PersistenceCapable
@Data
public class Usr {


    @Comment("邀请人 上级")

    @Column(name = "invtr", columnDefinition = "VARCHAR(255) COMMENT ' 邀请人 上级.'")
    public String invtr="";


    //@PrimaryKey
    @Id
    public String id;   //uid，， 和uname相同

    @NotBlank

    @Valid
    public String uname = "";  //和uid相同

    /** 主体标识符（必须） */
    public String sub;   //和uid相同  相当于uid别名

 

    //已经充值总额
    public BigDecimal alreadyRechgSum=new BigDecimal(0);;

 

    public Usr(String uname) {
        this.uname = uname;
        this.id=uname;
    }

    // public BigDecimal getTotalCommssionAmt() {
    //     if (totalCommssionAmt == null)
    //         return BigDecimal.valueOf(0);
    //     return toBigDcmTwoDot(totalCommssionAmt);
    // }

  

    public Usr(String uname, String pwd) {
        this.uname = uname;

    }

    public Usr() {

    }
    public String email = "";
    public  boolean enabled=true;

    @CreationTimestamp
    public long crtTimeStmp;

    public long timestamp=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmp;




    /** 用户全名 */
    private String name;

    /** 名 */
    private String givenName;

    /** 姓 */
    private String familyName;

    /** 中间名 */
    private String middleName;

    /** 昵称 */
    private String nickname;

    /** 用户名 */
    private String preferredUsername;

    /** 头像 URL */
    private String picture;

    /** 个人网站或主页 URL */
    private String profile;

    /** 用户所在地点的个人页面地址 */
    private String website;



    /** 邮箱是否已验证 */
    private Boolean emailVerified;

    /** 性别（"male"、"female" 或其他） */
    private String gender;

    /** 出生日期（格式：YYYY-MM-DD） */
    private String birthdate;

    /** 时区，例如 "Asia/Shanghai" */
    private String zoneinfo;

    /** 所在地国家/地区（ISO 3166-1） */
    private String locale;

    /** 电话号码（E.164 格式） */
    private String phoneNumber;

    /** 电话是否验证过 */
    private Boolean phoneNumberVerified;


    @UpdateTimestamp
    /** 最后更新时间（UNIX 时间戳） */
    private Instant updatedAt;
}
