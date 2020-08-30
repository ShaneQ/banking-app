package ie.shanequaid.banking.dto;

import ie.shanequaid.banking.annotation.IbanValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDepositRequestDTO {

    @IbanValidator
    private String iban;

    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private BigDecimal amount;
}
