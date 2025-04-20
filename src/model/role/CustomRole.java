package model.role;

import lombok.Data;
import model.auth.Role;

@Data
public class CustomRole {

    public Role role;
    public  String name;
    public boolean enable=true;
    public  long timestamp=System.currentTimeMillis();
}
