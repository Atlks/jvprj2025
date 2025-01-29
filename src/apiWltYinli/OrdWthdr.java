package apiWltYinli;
import jakarta.persistence.*;

import java.math.BigDecimal;

import static apiCms.CmsBiz.toBigDcmTwoDot;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

@Entity
@Table(name = "OrdWthdr")
public class OrdWthdr {
    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname;

    @Id
    public String id;

    public BigDecimal getAmt() {
        return toBigDcmTwoDot(amt) ;
    }

    public BigDecimal amt;

    public long getTimestamp() {
        return timestamp;
    }
}
