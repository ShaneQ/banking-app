package ie.shanequaid.banking.web.controller;

import ie.shanequaid.banking.web.model.AccountBalanceResponseDTO;
import ie.shanequaid.banking.web.model.AccountLockSettingRequestDTO;
import ie.shanequaid.banking.service.AccountService;
import org.apache.bval.extras.constraints.checkdigit.IBAN;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("account")
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("lock")
    public void lock(@Valid @RequestBody AccountLockSettingRequestDTO dto) {
        service.toggleLock(dto);
    }

    /*
        URL are recorded by the servers they pass through,
        this may not be the best solution, i would never have a
        unencrypted piece of confidential information as a param
     */

    @GetMapping("/{iban}/balance")
    public AccountBalanceResponseDTO lock(@IBAN @PathVariable("iban") String iban) {
        return service.getBalance(iban);
    }
}
