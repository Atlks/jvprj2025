package orgx.acc;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name="accounts")
@TableName("accounts")
@Data
public class Account {
    @TableId
    @Id
    private final String accId;
    private   String owner;
    public  String Status;
    public OffsetDateTime StatusUpdateDateTime;
    public String Currency;
    //AccountType,AccountSubType,Description

    public Account(String accid, String owner1) {
        this.owner=owner1;
        this.accId=accid;
    }
}
