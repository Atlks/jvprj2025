package util.model.openbank;


import util.Oosql.Column;

import java.util.*;

import static util.algo.CutJoin.trim4sql;

public class InsertIntoStmt {
    public String tableName;
    public Map<String, Column> linkedHashMap = new LinkedHashMap();

    public InsertIntoStmt(String tableName) {
        this.tableName = tableName;
    }

    public void set(String field, Column value) {
        linkedHashMap.put(field, value);
    }

    public String getSQL() {

        return "insert into " + tableName + " (" + trim4sql(linkedHashMap.keySet().toString()) + ") values (" + trim4sql(linkedHashMap.values().toString()) + ") ";
    }


    public void set(Column column) {
        linkedHashMap.put(column.getName(), column);
    }
}
