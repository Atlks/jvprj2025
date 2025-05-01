package model.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRqdto {
    @NotBlank
    public  String id="";

    public String stat;  //ReviewStat
    //@NotBlank
    public String IdempotencyKey="";
}
