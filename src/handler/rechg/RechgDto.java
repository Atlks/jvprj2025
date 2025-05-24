package handler.rechg;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import util.annos.CurrentUsername;

import java.math.BigDecimal;

@Data
public class RechgDto {
    @CurrentUsername
public  String owner;
    @NotNull
    public BigDecimal amount;
}
