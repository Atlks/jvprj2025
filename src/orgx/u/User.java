package orgx.u;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import orgx.UserEntityListener;
import orgx.uti.orm.EmlConverter;

import java.util.Set;

import static orgx.uti.Uti.encodeJson;
import static orgx.uti.Uti.encodeJsonDto;


@TableName("users")
@Entity
@Table(name = "users")
@Data
@EntityListeners(UserEntityListener.class)  // 注册监听器
public class User {
    @TableField(exist = false) // 这个字段不会映射到数据库
   // @Transient
    @Embedded
   // @Convert(converter = EmlConverter.class)
    public  Eml email;   //如何指定以json格式序列化


    public User(String alice) {
        setName(alice);
        setId(alice.hashCode());
    }


    @TableId // 主键
    @Id
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在 3 到 20 之间")
    @Expose
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    private int age;


    // 构造方法
    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    public static void main(String[] args) {
        User user = new User(1,"nmm",66);
        System.out.println(encodeJsonDto(user));
    }
}
