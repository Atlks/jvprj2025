package cfg;

import lombok.Data;

@Data
public class PageInfo {

    private int page=1;       // 当前页码
    private int pagesize=200;       // 每页大小
    private long totalRows=0;     // 总条数
    private int totalPages=1; // 总页数
}
