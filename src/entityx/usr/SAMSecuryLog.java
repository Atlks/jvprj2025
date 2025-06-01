package entityx.usr;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name="sam_secury_log")
public class SAMSecuryLog {

    @Id

    String id = "util/evt" + System.currentTimeMillis();
    String evtType;  //用户活动、系统错误、访问控制失败

    public  String timex;

    public long crtTimeStmp;
    String uname;
    String op;
    String dscrp;

}
