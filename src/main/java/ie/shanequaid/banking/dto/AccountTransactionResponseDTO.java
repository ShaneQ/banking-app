package ie.shanequaid.banking.dto;

import ie.shanequaid.banking.model.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountTransactionResponseDTO {

    private String iban;

    private List<Transaction> transactions;
}
