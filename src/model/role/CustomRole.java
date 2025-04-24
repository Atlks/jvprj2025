package model.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import model.auth.Role;
import util.algo.GetUti;

 

@Entity
@Table
@Data
public class CustomRole {

    public Role role;
    @Id
    public  String id= (String)GetUti. getUuid();
    public  String name;
    public boolean enable=true;
    public  long timestamp=System.currentTimeMillis();
}
