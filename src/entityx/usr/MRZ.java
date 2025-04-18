package entityx.usr;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MRZ {
    private String documentType;   // 文档类型 (V = Visa)
    private String issuingCountry; // 签发国 (e.g., "THA" for Thailand)
    private String visaNumber;     // 签证号
    private String holderSurname;  // 持有人姓氏
    private String holderGivenName;// 持有人名字
    private String passportNumber; // 护照号码
    private LocalDate issueDate;   // 签发日期
    private LocalDate expiryDate;  // 过期日期

    public MRZ(String issuingCountry, String visaNumber, String holderSurname,
               String holderGivenName, String passportNumber, LocalDate issueDate, LocalDate expiryDate) {
        this.documentType = "V"; // Visa 类型
        this.issuingCountry = issuingCountry;
        this.visaNumber = visaNumber;
        this.holderSurname = holderSurname;
        this.holderGivenName = holderGivenName;
        this.passportNumber = passportNumber;
      //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        this.issueDate = issueDate;
     //   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        this.expiryDate = expiryDate;
    }

    // 生成 MRZ 码（ICAO 9303 格式）
    public String generateMRZ() {
        // 第一行 (V<国家代码 + 签证号 + 填充字符)
        String line1 = String.format("V<%-3s%-9s<<<<<<<<<<<<", issuingCountry, visaNumber)
                .replace(" ", "<");

        // 第二行 (姓氏 + 名字 + 填充字符)
        String namePart = (holderSurname.toUpperCase() + "<<" + holderGivenName.toUpperCase()).replace(" ", "<");
        String line2 = String.format("%-31s", namePart).replace(" ", "<");

        return line1 + "\n" + line2;
    }

    @Override
    public String toString() {
        return "MRZ{" +
                "documentType='" + documentType + '\'' +
                ", issuingCountry='" + issuingCountry + '\'' +
                ", visaNumber='" + visaNumber + '\'' +
                ", holderSurname='" + holderSurname + '\'' +
                ", holderGivenName='" + holderGivenName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public static void main(String[] args) {
        MRZ visaMRZ = new MRZ(
                "THA", "12345678", "Doe", "John", "A1234567",
                LocalDate.of(2025, 2, 28), LocalDate.of(2025, 8, 28));

        System.out.println("签证机读区（MRZ）：");
        System.out.println(visaMRZ.generateMRZ());
    }
}
