package ie.shanequaid.banking.web.model;

import ie.shanequaid.banking.annotation.IbanValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AccountLockSettingRequestDTO {

    @IbanValidator
    private String iban;

    @NotNull
    private boolean locked;
}
