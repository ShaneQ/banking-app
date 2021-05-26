package ie.shanequaid.banking.web.controller;

import ie.shanequaid.banking.web.model.TransferDepositRequestDTO;
import ie.shanequaid.banking.web.model.TransferRequestDTO;
import ie.shanequaid.banking.domain.account.Account;
import ie.shanequaid.banking.domain.account.CheckingAccount;
import ie.shanequaid.banking.domain.account.PrivateLoanAccount;
import ie.shanequaid.banking.domain.account.SavingsAccount;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransferControllerTest extends ControllerTest {

    private static final String DEPOSIT_URL = "/transfer/deposit";
    private static final String TRANSFER_URL = "/transfer";

    @Test
    void deposit() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account account = new CheckingAccount(IBAN_ONE);
        doReturn(account).when(stateService).get(account.getIban());

        TransferDepositRequestDTO dto = new TransferDepositRequestDTO(account.getIban(), transactionAmount);
        mockMvc.perform(
                post(DEPOSIT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());

        verify(stateService).get(account.getIban());
        verify(stateService).update(account);

    }

    @Test
    void depositNegativeAmount() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(-40);

        Account account = new CheckingAccount(IBAN_ONE);
        doReturn(account).when(stateService).get(account.getIban());

        TransferDepositRequestDTO dto = new TransferDepositRequestDTO(account.getIban(), transactionAmount);
        mockMvc.perform(
                post(DEPOSIT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());

        verify(stateService, times(0)).get(account.getIban());
        verify(stateService, times(0)).update(account);

    }

    @Test
    void checkingToChecking() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new CheckingAccount(IBAN_TWO);
        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());
        addTransaction(debitAccount, 50);
        final int transactionsPreviousCount = debitAccount.getTransactions().size();


        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(true, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void depositToChecking() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);
        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        final int transactionsPreviousCount = addTransaction(debitAccount, 50);

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(true, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void debitAccountLocked() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);
        debitAccount.setLocked(true);
        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        final int transactionsPreviousCount = addTransaction(debitAccount, 50);

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(false, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void checkingToSavings() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);

        final int transactionsPreviousCount = addTransaction(debitAccount, 46);

        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isOk());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(true, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void creditAccountNotFound() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(45);

        Account creditAccount = new CheckingAccount(IBAN_ONE);

        Account debitAccount = new SavingsAccount(IBAN_TWO);

        addTransaction(debitAccount, 44);

        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isNotFound());

        verify(stateService, times(1)).get(creditAccount.getIban());

    }

    @Test
    void debitAccountNotFound() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(45);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);

        addTransaction(debitAccount, 44);

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isNotFound());

        verify(stateService, times(1)).get(debitAccount.getIban());

    }

    @Test
    void accountBalanceFailure() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(45);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);

        final int transactionsPreviousCount = addTransaction(debitAccount, 44);

        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(false, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void privateLoanToCheckingFailure() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new CheckingAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new PrivateLoanAccount(IBAN_TWO);
        final int transactionsPreviousCount = addTransaction(debitAccount, 50);

        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);
        dto.setCreditIban(creditAccount.getIban());
        dto.setAmount(transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(false, debitAccount, creditAccount, transactionsPreviousCount);

    }

    @Test
    void depositToPrivateLoanFailure() throws Exception {

        BigDecimal transactionAmount = new BigDecimal(40);

        Account creditAccount = new PrivateLoanAccount(IBAN_ONE);
        doReturn(creditAccount).when(stateService).get(creditAccount.getIban());

        Account debitAccount = new SavingsAccount(IBAN_TWO);
        final int transactionsPreviousCount = addTransaction(debitAccount, 50);

        doReturn(debitAccount).when(stateService).get(debitAccount.getIban());

        TransferRequestDTO dto = new TransferRequestDTO(creditAccount.getIban(), debitAccount.getIban(), transactionAmount);
        dto.setCreditIban(creditAccount.getIban());
        dto.setAmount(transactionAmount);

        mockMvc.perform(
                post(TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
        ).andExpect(status().isBadRequest());

        verify(stateService, times(1)).get(creditAccount.getIban());
        verify(stateService, times(1)).get(debitAccount.getIban());
        assertTransaction(false, debitAccount, creditAccount, transactionsPreviousCount);

    }


    private void assertTransaction(boolean isSuccessful, Account debitAccount, Account creditAccount, int transactionsPreviousCount) {

        if (isSuccessful) {
            assertEquals(transactionsPreviousCount + 1, debitAccount.getTransactions().size());
            assertEquals(1, creditAccount.getTransactions().size());
            verify(stateService, times(1)).update(creditAccount);
            verify(stateService, times(1)).update(debitAccount);
        } else {
            assertEquals(transactionsPreviousCount, debitAccount.getTransactions().size());
            assertEquals(0, creditAccount.getTransactions().size());
            verify(stateService, times(0)).update(creditAccount);
            verify(stateService, times(0)).update(debitAccount);
        }
    }


}