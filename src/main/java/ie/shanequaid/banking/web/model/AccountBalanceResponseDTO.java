package ie.shanequaid.banking.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountBalanceResponseDTO {

    @NotNull
    private BigDecimal balance;
}
