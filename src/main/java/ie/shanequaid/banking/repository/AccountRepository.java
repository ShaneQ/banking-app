package ie.shanequaid.banking.repository;

import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.account.AccountType;

import java.util.List;

public interface AccountRepository {

    Account get(String iban);

    void update(Account account);

    List<Account> getByAccountType(AccountType type);
}
