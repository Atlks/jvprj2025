package model.cfg;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class KvCfg {

    @Id
    public String k;

    @Column(length = 9999)
    public String v;

    public KvCfg(String k, String v) {
 this.k=k;
 this.v=v;
    }
}
