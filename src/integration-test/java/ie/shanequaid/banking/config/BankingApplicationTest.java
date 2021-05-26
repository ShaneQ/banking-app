package ie.shanequaid.banking.config;

import ie.shanequaid.banking.BankingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankingApplication.class)
class BankingApplicationTest {

    @Test
    void contextLoads() {
    }

}
