package com.example.bossi.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    private static final String API_NAME = "Bossi API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Bossi 웹 어플리케이션 API입니다.";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                //.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.bossi"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact().url("https://areuma.github.io/").email("kuuniin@gmail.com"))
                ;

        return new OpenAPI().components(new Components()).info(info);
    }
}
