package biz;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.regex.Pattern;
import  jakarta.validation.constraints.Min;

public class MinValidator implements ConstraintValidator<jakarta.validation.constraints.Min, BigDecimal> {

    private static final Pattern PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    Min constraintAnnotation;
    @Override
    public void initialize(Min constraintAnnotation) {
        this.constraintAnnotation=constraintAnnotation;
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null check
        }
        // Ensure value is a valid amount with up to two decimal places
       if( value.compareTo( BigDecimal.valueOf(constraintAnnotation.value()) )>=0 )
           return  true;
       else
           return  false;
      //  return PATTERN.matcher(value.toString()).matches();
    }
}

