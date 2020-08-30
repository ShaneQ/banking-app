package ie.shanequaid.banking.model.transfer;

import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.transaction.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Transfer {

    private Transaction credit;

    private Account creditAccount;

    public Transfer(Transaction credit, Account creditAccount) {
        this.credit = credit;
        this.setCreditAccount(creditAccount);
    }

}
