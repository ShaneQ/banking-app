package ie.shanequaid.banking.model.transfer;

import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.transaction.Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DepositTransfer extends Transfer {

    public DepositTransfer(Transaction credit, Account creditAccount) {
        super(credit, creditAccount);
    }


}
