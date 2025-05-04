package util.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public   class DataCommonField {


    public static String id;


    public static String createdAt;

    public static String updatedAt;

    public static String createdBy;

    public static String updatedBy;
    public static String timestamp="timestamp";

    public static String isDeleted = "isDeleted";

    public static String version = "ver";

    public static String status = "status";


    public static String ip_address;
    public static String       user_agent;
    public static String       remark;
    public static String               locale;
    public static String  tenant_id;



}
