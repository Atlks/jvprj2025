package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Data
public class Key {

    public Key() {
        this.salt = BCrypt.gensalt(); // 生成盐
     //   this.hashedPassword = BCrypt.hashpw(password, salt); // 哈希密码
    }
    @Id
    public String userId;   //uname
    public String hashedPassword;//ecry key
    public String salt;
}
