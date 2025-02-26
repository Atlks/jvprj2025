package entityx;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
public class baseObj {

    public  int pagesize=10;
    public  int page=1;

    @CreationTimestamp
    public long crtTimeStmp;

    @UpdateTimestamp
    public long updtTmstmp;
}
