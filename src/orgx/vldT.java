package orgx;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import orgx.u.User;

import java.util.Set;

import static orgx.uti.Uti.valdt;

public class vldT {

    public static void main(String[] args) {
        User user = new User();
        user.setName("2");
        valdt(user);
    }


}
