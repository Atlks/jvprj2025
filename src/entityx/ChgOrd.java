package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table
public class ChgOrd {
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
