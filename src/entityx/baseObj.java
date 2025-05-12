package entityx;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//for do
@Data
public class baseObj {

    public  int debugMode=0;
    public  int page=1;

    public  int   pagesize=200;

    public int  offset=0;  //偏移量

    public int limit=200;

  //  @NotBlank    //most need auth   ,,if not need uname,just fill defUsre
    public String uname="";

    // @CreationTimestamp
    public long crtTimeStmp=System.currentTimeMillis();

   // @UpdateTimestamp
    public long updtTmstmp=System.currentTimeMillis();

    public long   timestamp=System.currentTimeMillis();
}
