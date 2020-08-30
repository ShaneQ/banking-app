package ie.shanequaid.banking.model.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SavingsAccount extends Account {

    private static final AccountType type = AccountType.SAVINGS;

    public SavingsAccount(String iban) {
        super(iban);
    }

    @Override
    public AccountType getAccountType() {
        return type;
    }

    @Override
    public boolean canDebitBy(AccountType type) {
        return type.equals(AccountType.CHECKING);
    }

    @Override
    public boolean canCreditBy(AccountType type) {
        return true;
    }

}
