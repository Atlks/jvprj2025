package util.model.bet;

//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import entityx.baseObj;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static util.misc.util2026.getFilenameFrmLocalTimeString;
//  must jakarta not javax

/**
 * 注单 投注单
 */
@Entity
@Table(name = "OrdBet")
@Data
@NoArgsConstructor
public class BetOrd extends baseObj {



    @Id
    public String id;


    @Comment("注单日期")
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
