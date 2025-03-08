package entityx;

//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import static util.misc.util2026.getFilenameFrmLocalTimeString;
//  must jakarta not javax

@Entity
@Table(name = "OrdBet")
@Data
@NoArgsConstructor
public class BetOrd extends baseObj {



    @Id
    public String id;

    @NotBlank
    public String drawDate="";
    @NotNull
    public long timestamp;
    @NotBlank
    @Column(name = "uname", nullable = false)
    public String uname="";

    @NotBlank
    public String bettxt="";

    public BetOrd(BetOrdDto dto) {
        timestamp = System.currentTimeMillis();
        this.uname = dto.uname;
        id = "ordBet" + getFilenameFrmLocalTimeString();
    }
}
