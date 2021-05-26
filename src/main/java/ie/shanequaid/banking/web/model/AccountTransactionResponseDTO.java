package ie.shanequaid.banking.web.model;

import ie.shanequaid.banking.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountTransactionResponseDTO {

    private String iban;

    private List<Transaction> transactions;
}
