package util.annos;

import model.auth.Role;

public @interface NeedAuth {
    Role value();
}
