package entityx.wlt;

import entityx.baseObj;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Table
@Data
public class LogCms extends baseObj {

    @Id
    public String id;
    public String uname;
    public BigDecimal commssionAmt;
}
