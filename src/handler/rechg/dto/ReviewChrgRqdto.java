package handler.rechg.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewChrgRqdto {
    @NotBlank
    public  String transactionId="";
    //@NotBlank
    public String IdempotencyKey="";
}
