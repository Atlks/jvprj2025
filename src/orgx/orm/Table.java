package orgx.orm;


import lombok.Data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class Table {
    private List<Column> EMPTY_COLUMN_ARRAY = new ArrayList<>();
    private String name="";
    private   Map<String, Column> columns=new HashMap<>();
    //  private PrimaryKey primaryKey;
    private String comment="";
    private String    primaryKey;
}
