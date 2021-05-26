package ie.shanequaid.banking.validation;

import org.apache.bval.extras.constraints.checkdigit.IBANValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IbanValidator implements
        ConstraintValidator<ie.shanequaid.banking.annotation.IbanValidator, String> {

    @Override
    public void initialize(ie.shanequaid.banking.annotation.IbanValidator ibanValidator) {

    }

    /**
     * Imported package has internal NPE, created custom validator to wrap
     **/
    @Override
    public boolean isValid(String iban, ConstraintValidatorContext cxt) {

        if (iban == null) {
            return false;
        } else {
            return new IBANValidator().isValid(iban, cxt);
        }

    }

}