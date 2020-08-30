package ie.shanequaid.banking.controller;

import ie.shanequaid.banking.dto.TransferDepositRequestDTO;
import ie.shanequaid.banking.dto.TransferRequestDTO;
import ie.shanequaid.banking.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("transfer")
@RestController
@Slf4j
public class TransferController {

    private TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("deposit")
    public UUID deposit(@Valid @RequestBody TransferDepositRequestDTO dto) {
        return service.deposit(dto);
    }

    @PostMapping("")
    public UUID transfer(@Valid @RequestBody TransferRequestDTO dto) {
        return service.payment(dto);
    }
}
