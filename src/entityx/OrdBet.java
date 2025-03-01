package entityx;

//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;
//  must jakarta not javax

@Entity
@Table(name = "OrdBet")
@Data
public class OrdBet extends baseObj {



    @Id

    public String id;

    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname="";
    public String bettxt;
}
