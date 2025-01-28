package apiOrdBet;


import javax.persistence.*;

@Entity
@Table(name = "OrdBet")
public class OrdBet {



    @Id

    public String id;

    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname;
}
