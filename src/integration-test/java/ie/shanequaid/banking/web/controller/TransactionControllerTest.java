package ie.shanequaid.banking.web.controller;

import ie.shanequaid.banking.domain.account.Account;
import ie.shanequaid.banking.domain.account.AccountType;
import ie.shanequaid.banking.domain.account.CheckingAccount;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest extends ControllerTest {

    private static final String LIST_BY_ACCOUNT_TYPE_URL = "/transaction/list/type/%s";

    private static final String LIST_URL = "/transaction/list/%s";

    @Test
    void getTransactionList() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);
        doReturn(account).when(stateService).get(account.getIban());
        addTransaction(account, 50);

        mockMvc.perform(
                get(String.format(LIST_URL, account.getIban()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()).andReturn();

    }

    @Test
    void getTransactionListValidationError() throws Exception {

        Account account = new CheckingAccount(IBAN_ONE);
        doReturn(account).when(stateService).get(account.getIban());
        addTransaction(account, 50);

        mockMvc.perform(
                get(String.format(LIST_URL, "SOMETHING"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isBadRequest()).andReturn();

    }

    @Test
    void getTransactionListByAccountType() throws Exception {
        List<Account> list = new ArrayList<>();
        Account accountOne = new CheckingAccount(IBAN_ONE);
        addTransaction(accountOne, 50);
        Account accountTwo = new CheckingAccount(IBAN_TWO);
        addTransaction(accountTwo, 50);
        list.add(accountOne);
        list.add(accountTwo);

        doReturn(list).when(stateService).getByAccountType(AccountType.CHECKING);

        mockMvc.perform(
                get(String.format(LIST_BY_ACCOUNT_TYPE_URL, accountOne.getAccountType().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()).andReturn();

    }

    @Test
    void getTransactionListByAccountTypeEmptyList() throws Exception {

        mockMvc.perform(
                get(String.format(LIST_BY_ACCOUNT_TYPE_URL, AccountType.CHECKING.toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()).andReturn();

    }

    @Test
    void getTransactionListByAccountTypeValidation() throws Exception {

        mockMvc.perform(
                get(String.format(LIST_BY_ACCOUNT_TYPE_URL, "SOMETHING"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isBadRequest()).andReturn();

    }

    @Test
    void getTransactionListAccountNotFound() throws Exception {

        mockMvc.perform(
                get(String.format(LIST_BY_ACCOUNT_TYPE_URL, ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isBadRequest()).andReturn();

    }

    @Test
    void getTransactionListIbanMissing() throws Exception {

        mockMvc.perform(
                get(String.format(LIST_URL, ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isNotFound()).andReturn();

    }


}