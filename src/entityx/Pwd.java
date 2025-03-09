package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Data
public class Pwd {

    public Pwd() {
        this.salt = BCrypt.gensalt(); // 生成盐
     //   this.hashedPassword = BCrypt.hashpw(password, salt); // 哈希密码
    }

    public String userId;   //uname
    @Id
    public String username;
    public String hashedPassword;
    public String salt;
}
