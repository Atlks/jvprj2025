package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员
 */
@Entity
@Table(name = "Admin")
@Data
//@NoArgsConstructor
public class Admin {


   @Id           // 唯一主键
   public String username;     // 登录用户名
    public String password;     // 密码（建议加密）
    private String nickname;     // 显示用名称
    private String role;         // 角色（如：admin、editor）
    private boolean enabled;     // 是否启用
    private long createdAt;      // 创建时间戳
    private long updatedAt;      // 最后修改时间戳

    public Admin() {
    }

//    public Admin(int id, String username, String password, String nickname, String role, boolean enabled, long createdAt, long updatedAt) {
//       // this.id = id;
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//        this.role = role;
//        this.enabled = enabled;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }

//    // getter 和 setter 方法
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Admin{" +
              //  "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
