package apiAcc;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

import static apiCms.CmsBiz.toBigDcmTwoDot;
@Entity
@Table
public class OrdChrg {
    public String uname;

    public BigDecimal getAmt() {
        return  toBigDcmTwoDot(amt) ;
    }

    public BigDecimal amt=new BigDecimal(0);
    public long timestamp=0;

    @Id
    public String id;

    public String getUname() {
        return uname;
    }

    public String stat="";
}
