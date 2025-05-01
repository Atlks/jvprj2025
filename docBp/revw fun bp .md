

审核功能的实现 规范
普通的审核一个简单hdl就可以了

充值 和提现的审核比较负责，  complt和reject 需要分开hdl处理   牵扯到freeAmt的回退  和流水记录



# /xxmodel/applyReview?xxxx   提交审核
..oopenapi doc 
..java dto
@Entity
@Table(name = MyWltAddr.MY_WLT_ADDR)
@Data
//@NoArgsConstructor
public class MyWltAddr {


    public static final String MY_WLT_ADDR = "My_Wlt_Addr";
    @Id
    @CurrentUsername
    public  String uname="";
    public long timestamp=System.currentTimeMillis();



# xxx/listReview

# xxx/reviewHdl?id=xxx&stat=Completed       审核操作

public class ReviewRqdto {
@NotBlank
public  String id="";

    public String stat;  //ReviewStat
    //@NotBlank
    public String IdempotencyKey="";
}


public Object handleRequest(ReviewRqdto reqdto ) throws Throwable {



            MyWltAddr wp = findByHerbinate(MyWltAddr.class, reqdto.getId(), sessionFactory.getCurrentSession());
            wp.stat=fromCodeStr_ReviewStat(reqdto.stat) ;
            return (wp);


    }



---resp   apiGateWayResponse



public enum ReviewStat {
Completed,
Pending,
CANCELLED,
REJECTED,OTH;
