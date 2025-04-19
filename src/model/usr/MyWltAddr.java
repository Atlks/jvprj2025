package model.usr;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "My_Wlt_Addr")
@Data
//@NoArgsConstructor
public class MyWltAddr {


    @Id
    public  String uname="";
    // 币种，如 USDT、BTC 等
    public String currency="USDT";

    // 协议，如 ERC20、BEP20 等
    public String protocol="BEP20";

    // 收款地址
    public String address="";

    // 二维码图片 URL（可用于前端展示）
    public String qrCodeUrl="";

    public MyWltAddr() {
    }

    public MyWltAddr(String currency, String protocol, String address, String qrCodeUrl) {
        this.currency = currency;
        this.protocol = protocol;
        this.address = address;
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    @Override
    public String toString() {
        return "RechargeConfig{" +
                "currency='" + currency + '\'' +
                ", protocol='" + protocol + '\'' +
                ", address='" + address + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                '}';
    }
}

