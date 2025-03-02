package util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class OOValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static void validate(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("校验失败: \n");
            for (ConstraintViolation<Object> violation : violations) {
                errorMsg.append("- 字段 '").append(violation.getPropertyPath())
                        .append("': ").append(violation.getMessage()).append("\n");
            }
            throw new RuntimeException(errorMsg.toString());
        }
    }
}
