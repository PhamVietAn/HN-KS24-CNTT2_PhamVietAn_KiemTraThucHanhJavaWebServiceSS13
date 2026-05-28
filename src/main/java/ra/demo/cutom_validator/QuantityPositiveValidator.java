package ra.demo.cutom_validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityPositiveValidator implements ConstraintValidator<QuantityPositive, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        return value>=0;
    }
}
