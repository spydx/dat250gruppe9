package no.hvl.dat250.gruppe9;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("no.hvl.dat250.gruppe9"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true);
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "FeedApp REST API",
                "FeedApp Voting API.",
                "API PROTO",
                "Terms of service",
                new Contact("FeedApp Admin", "www.feedapp.com", "support@feedapp.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
