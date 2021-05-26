package ie.shanequaid.banking.service;

import ie.shanequaid.banking.web.model.AccountTransactionResponseDTO;
import ie.shanequaid.banking.domain.account.Account;
import ie.shanequaid.banking.domain.account.AccountType;
import ie.shanequaid.banking.domain.transaction.Transaction;
import org.apache.bval.extras.constraints.checkdigit.IBAN;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionService {

    private AccountService accountService;


    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Transaction> getList(@Valid @IBAN String iban) {
        Account account = accountService.getAccount(iban);
        return account.getTransactions();

    }

    public List<AccountTransactionResponseDTO> getList(AccountType type) {
        List<Account> accounts = accountService.getAccounts(type);
        List<AccountTransactionResponseDTO> accountTransactionResponseDTOS = new ArrayList<>();
        for (Account account : accounts) {
            AccountTransactionResponseDTO dto = new AccountTransactionResponseDTO(account.getIban(), account.getTransactions());
            accountTransactionResponseDTOS.add(dto);
        }
        return accountTransactionResponseDTOS;
    }
}
