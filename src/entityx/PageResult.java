package entityx;

import java.util.List;

// 分页结果封装类
public class PageResult<T> {
 public      List<T> records;
    public    long totalRecords;
    public   long totalPages;

    public PageResult(List<T> list1, long totalRecords, long totalPages) {
        System.out.println("\r\n");
        System.out.println("fun PageResult(list");
        System.out.println("  totalRecords="+totalRecords);
        System.out.println("  totalPages="+totalPages);
        System.out.println(" ))");
        records = list1;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        System.out.println("endfun PageResult()");

    }
}
