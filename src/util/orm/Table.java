package util.orm;



import lombok.Data;
import org.jetbrains.annotations.NotNull;
import util.Oosql.Column;

import java.util.*;
@Data
public class Table {
    private List<Column> EMPTY_COLUMN_ARRAY = new ArrayList<>();
    private String name="";
    private   Map<String, Column> columns=new HashMap<>();
    //  private PrimaryKey primaryKey;
    private String comment="";
    private String    primaryKey;
}
