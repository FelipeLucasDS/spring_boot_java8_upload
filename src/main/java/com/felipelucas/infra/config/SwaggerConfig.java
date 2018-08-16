package com.felipelucas.infra.config;

import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public final static String[] SWAGGER_RESOURCES = {
            "/",
            "/swagger-ui.html",
            "/webjars/springfox-swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs"
    };

    @Bean
    public Docket mainConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Microservice")
                .description("Swagger Documentation")
                .version("1.0")
                .contact(new Contact("Felipe Lucas", "", "felipelucasds@hotmail.com"))
                .build();
    }

}