package util.model.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
   // public  long totalElements;
    public   int totalPages;
    public   long total;  //rotal recds..not total pages
    public    int page;
    public  int pageNumber;
    public   int pageSize;
    public Pageable pageable;
    @Deprecated
    public PageResult(List<T> list1, long totalRecords, int totalPages,int page,int pagesize) {
        System.out.println("\r\n");
        System.out.println("fun PageResult(list");
        System.out.println("  totalRecords="+totalRecords);
        System.out.println("  totalPages="+totalPages);
        System.out.println(" ))");
        records = list1;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        this.page=page;this.pageSize =pagesize;
        System.out.println("endfun PageResult()");
        this.pageable= PageRequest.of(page, pagesize);
        this.total = (totalRecords);
        this.pageNumber=pageable.getPageNumber();

    }

    public PageResult(List<T> list1, long totalRecords, int totalPages,Pageable pageable) {
        System.out.println("\r\n");
        System.out.println("fun PageResult(list");
        System.out.println("  totalRecords="+totalRecords);
        System.out.println("  totalPages="+totalPages);
        System.out.println(" ))");
        records = list1;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        this.total = (totalRecords);
        this.pageNumber=pageable.getPageNumber();
        this.page=pageable.getPageNumber();this.pageSize =pageable.getPageSize();
        this.pageable=pageable;
        System.out.println("endfun PageResult()");

    }

//    @Deprecated
//    public PageResult(List<T> list1, long totalRecords, int totalPages) {
//        System.out.println("\r\n");
//        System.out.println("fun PageResult(list");
//        System.out.println("  totalRecords="+totalRecords);
//        System.out.println("  totalPages="+totalPages);
//        System.out.println(" ))");
//        records = list1;
//        this.totalPages = totalPages;
//        this.totalRecords = totalRecords;
//        System.out.println("endfun PageResult()");
//        this.total = (totalRecords);
//        this.pageable= PageRequest.of(page, 10);
//        this.pageNumber=pageable.getPageNumber();
//
//    }
}
