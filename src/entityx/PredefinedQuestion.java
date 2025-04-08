package entityx;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

/**
 *  预定义问题表（可选）
 */

@Entity
@Table(name = "predefined_questions")
@Data
//@NoArgsConstructor
public class PredefinedQuestion {

    @Id

    private Integer id;

    private String questionText;

    private Boolean isEnabled = true;

    public PredefinedQuestion() {}

    // Getters and Setters...

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
