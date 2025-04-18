package model.cfg;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table
@Data
public class KvCfg {

    @Id
    public String k;
    public Object v;
}
