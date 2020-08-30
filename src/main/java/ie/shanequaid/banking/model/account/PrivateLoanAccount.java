package ie.shanequaid.banking.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PrivateLoanAccount extends Account {

    private static final AccountType type = AccountType.PRIVATE_LOAN;

    public PrivateLoanAccount(String iban) {
        super(iban);
    }

    @Override
    public AccountType getAccountType() {
        return type;
    }

    @Override
    public boolean canDebitBy(AccountType type) {
        return false;
    }

    @Override
    public boolean canCreditBy(AccountType type) {
        return true;
    }

}
