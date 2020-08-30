package ie.shanequaid.banking.model.account;

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

}
