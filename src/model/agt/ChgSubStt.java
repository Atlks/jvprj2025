package model.agt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计充值的下级数量   重复排除 uname做key
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class ChgSubStt {

    @Id    
    public String uname;
    public String agtName;
    
}
