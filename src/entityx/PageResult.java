package entityx;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
// 分页结果封装类
public class PageResult<T> {
 public      List<T> records;
    public BigDecimal sum;  //统计总额

    //如果有多个统计总额的化，读写这里可以
    public Map<String,Object>  sumInfo=new HashMap<>();
    public    long totalRecords;
    public   int totalPages;
    public    int page;
    public   int pagesize;
    public PageResult(List<T> list1, long totalRecords, int totalPages,int page,int pagesize) {
        System.out.println("\r\n");
        System.out.println("fun PageResult(list");
        System.out.println("  totalRecords="+totalRecords);
        System.out.println("  totalPages="+totalPages);
        System.out.println(" ))");
        records = list1;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        this.page=page;this.pagesize=pagesize;
        System.out.println("endfun PageResult()");

    }
    public PageResult(List<T> list1, long totalRecords, int totalPages) {
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
