package ie.shanequaid.banking.model.account;

import ie.shanequaid.banking.model.transaction.Transaction;
import ie.shanequaid.banking.model.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Account {

    private String iban;

    private boolean locked;

    private List<Transaction> transactions;

    public Account(String iban) {
        this.iban = iban;
        transactions = new ArrayList<>();
    }

    public BigDecimal getBalance() {
        MathContext mc = new MathContext(2);
        BigDecimal sum = new BigDecimal("0");
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals(TransactionType.CREDIT)) {
                sum = sum.add(transaction.getAmount(), mc);
            } else {
                sum = sum.subtract(transaction.getAmount(), mc);
            }
        }

        return sum;
    }

    public abstract AccountType getAccountType();

    public boolean canDebitBy(AccountType type) {
        return true;
    }

    public boolean canCreditBy(AccountType type) {
        return true;
    }

}
