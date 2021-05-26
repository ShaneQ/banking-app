package ie.shanequaid.banking.domain.account;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class CheckingAccount extends Account {

    private static final AccountType type = AccountType.CHECKING;

    public CheckingAccount(String iban) {
        super(iban);
    }

    @Override
    public AccountType getAccountType() {
        return type;
    }

    @Override
    public boolean canDebitBy(AccountType type) {
        return true;
    }

    @Override
    public boolean canCreditBy(AccountType type) {
        return true;
    }

}
