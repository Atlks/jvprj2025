package entityx;

import lombok.Data;

@Data
public class Visa {
    private String visaNumber;
    private String country;  // 签证所属国家
    private String visaType;  // 例如旅游签、工作签等
    private String issueDate;
    private String expiryDate;
    private String status;  // 例如有效、过期等
 public    String passportNumber;
 public  Passport linkPassport;
 public  String MRZ1;
}
