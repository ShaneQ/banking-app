package ie.shanequaid.banking.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ie.shanequaid.banking.validation.IbanValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IbanValidator {
    String message() default "Invalid iban";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

