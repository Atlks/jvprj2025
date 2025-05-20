package util.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回操作的结果统计
 */
@Data
@NoArgsConstructor
public class CrudRzt {


//影响行数
    /** 影响的数据库行数 */
    private int affectedRows;

    /** 返回的数据（如新增主键、查询结果等） */
    private Object data;

    public CrudRzt(int i) {
        this.affectedRows = i;
    }
}
