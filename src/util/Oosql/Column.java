package util.Oosql;

import lombok.Data;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.mapping.CheckConstraint;

import java.util.ArrayList;
import java.util.List;

@Data
public class Column {
    private String name;       // 字段名（如 "user_id"）
    private String label;      // 字段中文名或注释（如 "用户ID"）
    private Object value;      // 字段的值（如 123）
    private Class<?> type;     // 字段类型（如 Integer.class）
    private boolean nullable = true;
    private boolean unique;
    private Size columnSize;
    int DecimalDigits;
    private Long length;
    private boolean identity;
    private String comment;
    private String defaultValue;
    private List<CheckConstraint> checkConstraints = new ArrayList();

    // 构造函数
    public Column(String name, String label, Object value, Class<?> type) {
        this.name = name;
        this.label = label;
        this.value = value;
        this.type = type;
    }

    public static Column newColumn(String name, Object value, Class<?> type) {
        //  this.name = name;
        // this.label = label;
        return new Column(name,value, type);
        // this.value = value;
        //  this.type = type;
    }
    public static Column newColumn(Object value, Class<?> type) {
        //  this.name = name;
        // this.label = label;
        return new Column(value, type);
        // this.value = value;
        //  this.type = type;
    }

    public Column(Object value, Class<?> type) {
        //  this.name = name;
        // this.label = label;
        this.value = value;
        this.type = type;
    }

    public Column(String name, Object value, Class<?> type) {
        this.name = name;
        // this.label = label;
        this.value = value;
        this.type = type;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (getType() == String.class) {
            String valueSafeEncode = value.toString().replaceAll("'", "''");
            return "'" + valueSafeEncode + "'";
        } else
            return String.valueOf(value);
//        return String.format("DbField{name='%s', label='%s', value=%s, type=%s}",
//                name, label, value, type.getSimpleName());
    }
}
