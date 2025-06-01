package model.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import model.auth.RoleType;
import org.hibernate.annotations.CreationTimestamp;
import util.algo.GetUti;
import util.annos.ObieFld;

import java.time.OffsetDateTime;

import static util.misc.Util2025.encodeJson;


@Entity
@Table
@Data
public class CustomRole {

    public String type=RoleType.ADMIN.name();

    public CustomRole(String name1) {
        this.name=name1;
    }

    public  void setType(RoleType type) {
        this.type = type.name();
    }
    @Id
    public  String name;

    public static void main(String[] args) {
        System.out.println(encodeJson(new CustomRole("运营人员")));
    }

}
