package entityx.wlt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;

@Entity
@Data
@Table(name = "ins_fd_pool_rcd")
public class InsFdPoolRcd {


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
