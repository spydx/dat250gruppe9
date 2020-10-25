package no.hvl.dat250.gruppe9.feedapp.restapi.config;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class ConfigSwagger {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    private final Logger log = LoggerFactory.getLogger(ConfigSwagger.class);

    @Bean
    public Docket api() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey(HttpHeaders.AUTHORIZATION, "JWT", "header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .securitySchemes(schemeList)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("no.hvl.dat250.gruppe9.feedapp.restapi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "FeedApp REST API",
                "FeedApp Voting API.",
                "API Production",
                "Terms of service",
                new Contact("FeedApp Admin", "www.feedapp.com", "support@feedapp.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
