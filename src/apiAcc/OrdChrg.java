package apiAcc;

import java.math.BigDecimal;

import static apiCms.CmsBiz.toBigDcmTwoDot;

public class OrdChrg {
    public String uname;

    public BigDecimal getAmt() {
        return  toBigDcmTwoDot(amt) ;
    }

    public BigDecimal amt=new BigDecimal(0);
    public long timestamp=0;
    public String id;

    public String getUname() {
        return uname;
    }

    public String stat="";
}
