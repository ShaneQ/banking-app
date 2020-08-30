package ie.shanequaid.banking.service;

import ie.shanequaid.banking.dto.TransferDepositRequestDTO;
import ie.shanequaid.banking.dto.TransferRequestDTO;
import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.transaction.Transaction;
import ie.shanequaid.banking.model.transaction.TransactionType;
import ie.shanequaid.banking.model.transfer.DepositTransfer;
import ie.shanequaid.banking.model.transfer.PaymentTransfer;
import ie.shanequaid.banking.problem.TransferProblem;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

@Component
public class TransferService {

    private AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public UUID deposit(TransferDepositRequestDTO dto) {
        Account account = accountService.getAccount(dto.getIban());
        Transaction transaction = new Transaction(dto.getAmount(), TransactionType.CREDIT);

        DepositTransfer transfer = new DepositTransfer(transaction, account);
        return transfer(transfer);
    }

    public UUID payment(TransferRequestDTO dto) {
        Account accountCredit = accountService.getAccount(dto.getCreditIban());
        Account accountDebit = accountService.getAccount(dto.getDebitIban());

        Transaction transactionCredit = new Transaction(dto.getAmount(), TransactionType.CREDIT);
        Transaction transactionDebit = new Transaction(dto.getAmount(), TransactionType.DEBIT);
        transactionDebit.setType(TransactionType.DEBIT);
        transactionCredit.setRef(transactionDebit.getRef());
        return transfer(new PaymentTransfer(transactionCredit, transactionDebit, accountDebit, accountCredit));
    }

    private UUID transfer(PaymentTransfer transfer) {
        validatePaymentTransfer(transfer);
        Account debitAccount = transfer.getDebitAccount();
        debitAccount.getTransactions().add(transfer.getDebit());
        accountService.save(debitAccount);

        Account creditAccount = transfer.getCreditAccount();
        Transaction credit = transfer.getCredit();
        credit.setRef(transfer.getDebit().getRef());
        creditAccount.getTransactions().add(credit);
        accountService.save(creditAccount);

        return credit.getRef();

    }

    private void validatePaymentTransfer(PaymentTransfer transfer) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PaymentTransfer>> violations = validator.validate(transfer);
        if(!violations.isEmpty()){
            throw new TransferProblem(violations.iterator().next().getMessage());
        }
    }

    private UUID transfer(DepositTransfer transfer) {
        Account creditAccount = transfer.getCreditAccount();
        Transaction credit = transfer.getCredit();
        creditAccount.getTransactions().add(credit);

        accountService.save(creditAccount);
        return credit.getRef();
    }

}
