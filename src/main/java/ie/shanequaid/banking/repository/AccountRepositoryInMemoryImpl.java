package ie.shanequaid.banking.repository;

import ie.shanequaid.banking.model.account.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AccountRepositoryInMemoryImpl implements AccountRepository {
    private Map<String, Account> accountMap;

    public AccountRepositoryInMemoryImpl(Map<String, Account> accountMap) {
        this.accountMap = accountMap;

        add(new CheckingAccount("IE29AIBK93115212345678"));
        add(new CheckingAccount("IQ20CBIQ861800101010500"));
        add(new SavingsAccount("DE75512108001245126199"));
        add(new PrivateLoanAccount("IS750001121234563108962099"));
    }

    public synchronized Account get(String iban) {
        return accountMap.get(iban);
    }

    public synchronized void update(Account account) {
        accountMap.put(account.getIban(), account);
    }

    public synchronized void add(Account account) {
        accountMap.put(account.getIban(), account);
    }

    public synchronized List<Account> getByAccountType(AccountType type) {
        List<Account> accounts = new ArrayList<>();

        Map<String, Account> map = this.accountMap;
        for (Map.Entry<String, Account> entry : map.entrySet()) {
            if (entry.getValue().getAccountType().equals(type)) {
                accounts.add(entry.getValue());
            }
        }
        return accounts;
    }
}