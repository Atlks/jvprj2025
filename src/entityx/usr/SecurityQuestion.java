package entityx.usr;

import util.annos.CurrentUsername;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.time.LocalDateTime;

/**
 * 密保问题（Security Question）」功能所需的实体类，
 */
@Entity
@Table(name = "Security_Question")
@Data
//@NoArgsConstructor
public class SecurityQuestion {

//    @Id
//    private Long id;
@CurrentUsername
    @Id
    public String userName;

    public String   questionId; // 对应预设问题表的ID

    public String customQuestionText; // 自定义问题内容（如果不是预设）
    public String answer; // 加密后的答案
    private String encryptedAnswer; // 加密后的答案

    public LocalDateTime createdAt = LocalDateTime.now();

    public Boolean isActive = true;

    // 构造函数
    public SecurityQuestion() {}

    // Getters and Setters





}

