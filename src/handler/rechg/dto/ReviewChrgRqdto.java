package handler.rechg.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static util.algo.GetUti.getUuid;

@Data
public class ReviewChrgRqdto {
    @NotBlank
    public  String transactionId="";
    @NotBlank
    public String IdempotencyKey=getUuid();
}
