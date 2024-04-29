package cn.edu.moe.smiling.datasource.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    private List<String> enumStringList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enumStringList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return enumStringList.contains(value);
    }
}
