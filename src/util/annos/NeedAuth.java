package util.annos;

import model.auth.RoleType;

public @interface NeedAuth {
    RoleType value();
}
