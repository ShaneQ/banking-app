package ie.shanequaid.banking.model.transfer;

import ie.shanequaid.banking.annotation.TransferValidator;
import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TransferValidator
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransfer extends Transfer {

    private Account debitAccount;

    private Transaction debit;

    public PaymentTransfer(Transaction credit, Transaction debit, Account debitAccount, Account creditAccount) {
        super.setCredit(credit);
        super.setCreditAccount(creditAccount);
        this.debit = debit;
        this.debitAccount = debitAccount;
    }

}
