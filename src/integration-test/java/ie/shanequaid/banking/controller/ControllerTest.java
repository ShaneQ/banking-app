package ie.shanequaid.banking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.shanequaid.banking.configuration.BankingApplication;
import ie.shanequaid.banking.model.account.Account;
import ie.shanequaid.banking.model.transaction.Transaction;
import ie.shanequaid.banking.model.transaction.TransactionType;
import ie.shanequaid.banking.repository.AccountRepositoryInMemoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankingApplication.class
)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class ControllerTest {

    public static final String IBAN_ONE = "IE29AIBK93115212345678";
    public static final String IBAN_TWO = "IS750001121234563108962099";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public AccountRepositoryInMemoryImpl stateService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int addTransaction(Account account, int amount) {


        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new BigDecimal(amount), TransactionType.CREDIT));
        account.setTransactions(transactions);
        return transactions.size();
    }

}
