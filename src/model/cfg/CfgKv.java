package model.cfg;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import static util.misc.Util2025.encodeJson;

@Entity
@Table(name = "cfgkv")  // 强制指定表名
@Data
@NoArgsConstructor
public class CfgKv {

    @Id
    public String k;

    //配置说明
    public String dscrpt;

    @Column(length = 9999)
    public String v;

    public CfgKv(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public CfgKv(String k, Object dataSummary1) {
        this.k = k;
        this.v = encodeJson(dataSummary1);

    }
}
