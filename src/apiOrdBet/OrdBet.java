package apiOrdBet;

//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import biz.baseObj;
import jakarta.persistence.*;
//  must jakarta not javax

@Entity
@Table(name = "OrdBet")
public class OrdBet extends baseObj {



    @Id

    public String id;

    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname="";
    public String bettxt;
}
