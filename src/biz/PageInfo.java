package biz;

import lombok.Data;

@Data
public class PageInfo {

    private int page=1;       // 当前页码
    private int size=50;       // 每页大小
    private long total=0;     // 总条数
    private int totalPages=0; // 总页数
}
