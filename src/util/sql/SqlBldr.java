package util.sql;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountType;
import orgx.uti.expressions.SumExpress;
import orgx.uti.orm.JPAQueryX;

import static util.Oosql.SqlBldr.toSqlPrmAsStr;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.GetUti.getTablename;
import static util.algo.ToXX.toSnake;

@Data
@Accessors(chain = true)
public class SqlBldr {
    String sql="";
    public   util.sql.SqlBldr select(String flds) {
        this.sql+="select "+flds;
        return  this;
    }
    public static String sum(String flds) {
        return "sum(" + getFldName(flds)  + ")";
    }

    public static String getFldName(String flds) {
        return  toSnake(flds);
    }

    public <T> SqlBldr from(Class<?> accountClass) {
        this.sql+=" from "+getTablename(accountClass);
        return  this;
    }

    public <T> SqlBldr where(String accountSubType, String s, String name) {
        this.sql+=" where "+getFldName(accountSubType)+s+toSqlPrmAsStr(name);
        return  this;
    }

    public static String toSqlPrmAsStr(@NotBlank @Valid String uname) {
        return  encodeSqlPrmAsStr(uname);
    }

    public SqlBldr and(String fld, String op, String  v) {
        this.sql+=" and "+getFldName(fld)+op+toSqlPrmAsStr(v);
        return this;
    }
}
