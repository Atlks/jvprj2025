package apiWltYinli;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
@Entity
@Table
public class LogCms {

    @Id
    public String id;
    public String uname;
    public BigDecimal commssionAmt;
}
