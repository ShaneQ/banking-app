package ie.shanequaid.banking.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ie.shanequaid.banking.validation.TransferValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransferValidator {
    String message() default "Invalid Transfer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

