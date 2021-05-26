package ie.shanequaid.banking.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private UUID ref;

    private TransactionType type;

    private BigDecimal amount;

    public Transaction(BigDecimal amount, TransactionType type) {
        this.ref = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
    }

}
