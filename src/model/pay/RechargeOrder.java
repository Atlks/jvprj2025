package model.pay;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import model.constt.RechargeOrderStat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static service.CmsBiz.toBigDcmTwoDot;
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table(name = "Recharge_Order")
@Data
public class RechargeOrder {


//  ISO 20022  标准字段
    @Id
    public String endToEndId;     //客户唯一订单号 ISO 20022 <EndToEndId>
    public BigDecimal instdAmt;  // <InstdAmt> 金额
    private String currency="usdt";      // <InstdAmt Ccy="">
    public String debtorId;      // <Dbtr> 发起方
    public String creditorId;    // <Cdtr> 平台收款账号
    @CreationTimestamp
    private long createTime;  // <CreDtTm>

    // <GrpHdr><Sts>  //审核状态   PNDG/WAIT=待审核
    public String status= String.valueOf(RechargeOrderStat.PNDG);


 //   public String stat="待审核";   //待审核  ，通过，拒绝
 public String initiator;          // <InitgPty> 处理人/审核人

    public String note="";              // 备注

    //finish  ISO 20022

//在 ISO 20022 中，<GrpHdr><Sts>（Group Header Status）是用来描述消息或交易的总体状态的字段。它通常用于支付、转账等类型的消息中，表示整个交易组的处理状态。
    //---------本地自定义扩展字段
    public String id;

    private String paymentChannel="usdt";     // usdt/微信/支付宝等
    private String channelOrderNo;     // 第三方返回单号




    @NotBlank
    public String uname="";

    //review time
    public long review_timestamp=0;

    public long   timestamp=System.currentTimeMillis();



    public String getUname() {
        return uname;
    }

    public String setUname() {
        return uname;
    }






    //审核人

    public String parentProxy="";
    public String vipLev="";

    //截图附件
    public String img="";

    @CreationTimestamp
    public long crtTimeStmp=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmp=System.currentTimeMillis();

   // public long   timestamp=System.currentTimeMillis();

}
