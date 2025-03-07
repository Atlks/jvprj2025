package entityx;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
public class baseObj {

    public  int page=1;

    public  int   pagesize=200;

    public String uname="";

    @CreationTimestamp
    public long crtTimeStmp=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmp=System.currentTimeMillis();
}
