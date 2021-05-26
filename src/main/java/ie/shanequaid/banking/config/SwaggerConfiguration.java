package ie.shanequaid.banking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@ConfigurationProperties(prefix = "swagger")
@Profile({"integration-test", "default", "dev", "qa"})
@Data
public class SwaggerConfiguration {

    private String title;
    private String description;
    private String host;
    private String version;
    private String consumes;
    private String produces;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(getPaths())
                .build()
                .host(host)
                .consumes(consumes())
                .produces(produces())
                .apiInfo(apiInfo());
    }

    private Predicate<String> getPaths() {
        return Predicate.not(PathSelectors.regex("/actuator.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title).version(version).description(description).build();
    }

    private Set<String> consumes() {
        return toSet(consumes);
    }

    private Set<String> produces() {
        return toSet(produces);
    }

    private Set<String> toSet(String commaDelimitedString) {
        String[] items = commaDelimitedString.split(",");
        Set<String> itemSet = new HashSet<>(items.length);
        Collections.addAll(itemSet, items);
        return itemSet;

    }
}
