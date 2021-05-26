package ie.shanequaid.banking.repository;

import ie.shanequaid.banking.domain.account.Account;
import ie.shanequaid.banking.domain.account.AccountType;

import java.util.List;

public interface AccountRepository {

    Account get(String iban);

    void update(Account account);

    List<Account> getByAccountType(AccountType type);
}
