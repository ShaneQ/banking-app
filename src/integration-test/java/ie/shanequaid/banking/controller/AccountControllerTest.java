package ie.shanequaid.banking.controller;


import ie.shanequaid.banking.dto.AccountLockSettingRequestDTO;
import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.account.CheckingAccount;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends ControllerTest {

    private static final String LOCK_SETTING_URL = "/account/lock";
    private static final String BALANCE_URL = "/account/%s/balance";


    @Test
    void unlockSuccess() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);

        doReturn(account).when(stateService).get(account.getIban());

        AccountLockSettingRequestDTO dto = new AccountLockSettingRequestDTO();
        dto.setIban(account.getIban());
        mockMvc.perform(
                post(LOCK_SETTING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());
        assertFalse(account.isLocked());

    }

    @Test
    void unlockValidationFailure() throws Exception {

        AccountLockSettingRequestDTO dto = new AccountLockSettingRequestDTO();
        dto.setIban("SOMETHING");
        mockMvc.perform(
                post(LOCK_SETTING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());
        verify(stateService, times(0)).update(any(Account.class));

    }

    @Test
    void lockSuccess() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);

        doReturn(account).when(stateService).get(account.getIban());

        AccountLockSettingRequestDTO dto = new AccountLockSettingRequestDTO();
        dto.setIban(account.getIban());
        dto.setLocked(true);
        mockMvc.perform(
                post(LOCK_SETTING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());
        assertTrue(account.isLocked());

    }

    @Test
    void accountBalance() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);

        doReturn(account).when(stateService).get(account.getIban());

        mockMvc.perform(
                get(String.format(BALANCE_URL, account.getIban()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    void accountBalanceValidationError() throws Exception {

        mockMvc.perform(
                get(String.format(BALANCE_URL, "SOMETHING"))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Test
    void accountBalanceNotAccountFound() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);

        mockMvc.perform(
                get(String.format(BALANCE_URL, account.getIban()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }
}