package apiOrdBet;

//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.*;
//  must jakarta not javax

@Entity
@Table(name = "OrdBet")
public class OrdBet {



    @Id

    public String id;

    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname;
}
