package entityx.usr;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import static util.algo.GetUti.getUuid;
// org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Data

public class Keyx {

    public Keyx() {
        this.salt = gensalt(); // 生成盐
     //   this.hashedPassword = BCrypt.hashpw(password, salt); // 哈希密码
    }

    private String gensalt() {
      return  getUuid();
    }

    @Id
    public String userId;   //uname

    //如何定义长度
    @Column(length = 600)
    public String hashedPassword;//ecry key
    public String salt="";
    public  String type="pwd";  //pwd ,print ,faceid
    public  boolean frz=false;//freeze
}
