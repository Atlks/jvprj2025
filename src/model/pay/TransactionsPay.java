package model.pay;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import model.constt.TransactionStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 扩展来自 OpenBanking的Transactions实体 ，为支付订单专门实体
 */
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table(name = "Transactions_Pay")  //Transactions_Pay
@Data
public class TransactionsPay {


//  ISO 20022  标准字段 金额 instdAmt
    @Id
    public String transactionId;     //客户唯一订单号 ISO 20022 <EndToEndId>
    public BigDecimal amount;  // <InstdAmt>
    private String currency="usdt";      // <InstdAmt Ccy="">
    public String debtorId;      //
    public String creditorId;    //
    @CreationTimestamp
    public Date bookingDate;  //

    // 交易金额有效入账的日期
    public Date valueDate;

     //审核状态   PNDG/WAIT=待审核
    public String transactionStatus = String.valueOf(TransactionStatus.PENDING);


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

    @CreationTimestamp   //20240505112233
    public long crtTimeStmpLocalfmt=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmpLocalfmt=System.currentTimeMillis();

   // public long   timestamp=System.currentTimeMillis();

}
