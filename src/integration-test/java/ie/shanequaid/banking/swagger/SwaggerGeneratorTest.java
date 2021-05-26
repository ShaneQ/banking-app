package ie.shanequaid.banking.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ie.shanequaid.banking.BankingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankingApplication.class
)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
class SwaggerGeneratorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateSwaggerDocs() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> new YAMLMapper().writeValue(
                        new File("banking.yaml"),
                        new ObjectMapper().readTree(
                                mvcResult.getResponse().getContentAsString())))
                .andExpect(status().isOk());

    }
}
