package entityx;

import lombok.Data;
import java.util.List;

@Data
public class Passport {
    private String passportNumber;
    private String nationality;
    private String holderName;
    private String dateOfBirth;
    private String issueDate;
    private String expiryDate;
    private String issuingCountry;
    private List<Visa> visas;  // 关联的签证列表
}
