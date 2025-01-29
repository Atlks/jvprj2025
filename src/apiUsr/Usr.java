package apiUsr;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
@Entity
@Table
public class Usr {
    public String uname="";
    public String invtr;
    public String pwd;
@Id
    public String id;
    public BigDecimal balance;// avdBls
    public BigDecimal balanceFreez;// avdBls
    public BigDecimal balanceYinliwlt;
    public BigDecimal balanceYinliwltFreez;
    public BigDecimal totalCommssionAmt;
}
