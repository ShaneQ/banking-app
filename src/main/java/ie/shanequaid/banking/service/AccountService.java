package ie.shanequaid.banking.service;

import ie.shanequaid.banking.dto.AccountBalanceResponseDTO;
import ie.shanequaid.banking.dto.AccountLockSettingRequestDTO;
import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.account.AccountType;
import ie.shanequaid.banking.problem.AccountNotFoundProblem;
import ie.shanequaid.banking.repository.AccountRepository;
import ie.shanequaid.banking.repository.AccountRepositoryInMemoryImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    private AccountRepository state;

    public AccountService(AccountRepositoryInMemoryImpl state) {
        this.state = state;
    }

    public Account getAccount(String iban) {

        Account account = state.get(iban);
        if (account == null) {
            throw new AccountNotFoundProblem();
        } else {
            return account;
        }

    }

    public AccountBalanceResponseDTO getBalance(String iban) {

        Account account = this.getAccount(iban);

        AccountBalanceResponseDTO dto = new AccountBalanceResponseDTO();
        dto.setBalance(account.getBalance());

        return dto;

    }

    public List<Account> getAccounts(AccountType type) {
        return state.getByAccountType(type);
    }

    public void toggleLock(AccountLockSettingRequestDTO dto) {
        Account account = getAccount(dto.getIban());
        account.setLocked(dto.isLocked());
        save(account);
    }

    public void save(Account account) {
        state.update(account);
    }
}
