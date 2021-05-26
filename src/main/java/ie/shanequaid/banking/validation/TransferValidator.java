package ie.shanequaid.banking.validation;

import ie.shanequaid.banking.domain.transfer.PaymentTransfer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransferValidator implements
        ConstraintValidator<ie.shanequaid.banking.annotation.TransferValidator, PaymentTransfer> {


    @Override
    public void initialize(ie.shanequaid.banking.annotation.TransferValidator transferValidator) {

    }

    @Override
    public boolean isValid(PaymentTransfer transfer, ConstraintValidatorContext cxt) {

        if (hasSufficientFunds(transfer)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Debit Account has insufficient funds").addConstraintViolation();
            return false;
        }

        if (transfer.getDebitAccount().isLocked()) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Debit Account is locked").addConstraintViolation();
            return false;
        }
        if (!isValidAccountActions(transfer)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(
                    String.format("%s Account can not be credited by %s", transfer.getDebitAccount().getAccountType().toString(), transfer.getCreditAccount().getAccountType().toString())
            ).addConstraintViolation();
            return false;
        }
        return true;

    }

    private boolean hasSufficientFunds(PaymentTransfer transfer) {
        return transfer.getDebitAccount().getBalance().compareTo(transfer.getCredit().getAmount()) <= 0;

    }

    private boolean isValidAccountActions(PaymentTransfer transfer) {
        return transfer.getDebitAccount().canDebitBy(transfer.getCreditAccount().getAccountType()) &&
                transfer.getCreditAccount().canCreditBy(transfer.getDebitAccount().getAccountType());
    }

}