package ie.shanequaid.banking.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class TemplateConfiguration {

    @Bean
    @Primary
    public ObjectMapper mapper() {
        return new ObjectMapper().
                registerModules(new ProblemModule(), new ConstraintViolationProblemModule());
    }

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
