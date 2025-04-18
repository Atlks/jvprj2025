package model;

public class VisaConstants {

    public static String visaNumber="visaNumber";
    public static final String country="country";       // 签证所属国家
    public static String visaType="visaType";      // 例如旅游签、工作签等
    public static String issueDate="issueDate";
    public static String expiryDate="expiryDate";
    public static String status="status";        // 例如有效、过期等

    public static  String passportNumber="passportNumber";
    public static  String linkPassport="linkPassport";
    public static  String MRZ="MRZ";

    // 签证类型（visaType）
    public static final String TYPE_vistor = "vistor";     // 旅游
    public static final String TYPE_TOURIST = "TOURIST";     // 旅游签
    public static final String TYPE_WORK = "WORK";           // 工作签
    public static final String TYPE_STUDENT = "STUDENT";     // 学生签
    public static final String TYPE_FAMILY = "FAMILY";       // 家属签
    public static final String TYPE_TRANSIT = "TRANSIT";     // 过境签

    // 签证状态（status）
    public static final String STATUS_VALID = "VALID";       // 有效
    public static final String STATUS_EXPIRED = "EXPIRED";   // 过期
    public static final String STATUS_REVOKED = "REVOKED";   // 撤销
    public static final String STATUS_PENDING = "PENDING";   // 待审
}

