package apiUsr;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.math.BigDecimal;

// C:\Users\attil\.m2\repository\javax\jdo\jdo-api\3.2.1\jdo-api-3.2.1.jar
@Entity
@Table
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
    public BigDecimal totalCommssionAmt;

    public Usr(String uname, String pwd) {
   this.uname=uname;
   this.pwd=pwd;
    }

    public Usr() {

    }
}
