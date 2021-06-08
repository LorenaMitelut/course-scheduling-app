package com.endava.courseschedulingapp.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemes())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        return List.of(new BasicAuth("basic"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Online Course Scheduling API")
                .description("Online Courses Scheduling Application")
                .version("1.0")
                .contact(new Contact("Lorena Mitelut", "http://localhost:8080/swagger-ui.html", "myEmail@edava.com"))
                .build();
    }

    private Predicate<String> paths() {
        return Predicates.or(regex("/users.*"),
                regex("/course.*"));
    }
}
