package ie.shanequaid.banking.controller;

import ie.shanequaid.banking.dto.AccountTransactionResponseDTO;
import ie.shanequaid.banking.model.account.AccountType;
import ie.shanequaid.banking.model.transaction.Transaction;
import ie.shanequaid.banking.problem.AccountTypeProblem;
import ie.shanequaid.banking.service.TransactionService;
import org.apache.bval.extras.constraints.checkdigit.IBAN;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("transaction")
public class TransactionController {
    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("list/{iban}")
    public List<Transaction> getListByAccount(@Valid @IBAN @PathVariable("iban") String iban) {
        return service.getList(iban);
    }

    @GetMapping("list/type/{type}")
    public List<AccountTransactionResponseDTO> getListByAccountType(@PathVariable("type") String type) {
        AccountType accountType;
        try {
            accountType = AccountType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            throw new AccountTypeProblem();
        }
        return service.getList(accountType);
    }
}
