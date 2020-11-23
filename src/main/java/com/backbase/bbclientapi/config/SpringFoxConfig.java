package com.backbase.bbclientapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.backbase.bbclientapi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Transaction Api",
                "This application provides api for retrieving transaction data from OpenBank sandbox",
                "1.0.0",
                "TERMS OF SERVICE URL",
                new Contact("Szabolcs Csiszer", "https://www.linkedin.com/in/szabolcs-csiszer", "csiszersz@gmail.com"),
                null,
                null,
                Collections.emptyList()
        );
    }
}
