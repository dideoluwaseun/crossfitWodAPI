package com.seun.crossfitWodAPI.swagger.configuration;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey())).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.seun.crossfitWodAPI.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Crossfit WOD API", "</br></br><h3> **Note**: This API requires an `API KEY`, please log into your account to access your key </h3>.", "1.0", "Terms of Service", new Contact("Seun", "seun@gmail.com", "seun@gmail.com"), "", "", Collections.emptyList());
    }

    private ApiKey apiKey(){
        return new ApiKey("Token Access", "Authorization", SecurityScheme.In.HEADER.name());
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("Unlimited", "Full API Permission")};
    return Collections.singletonList(new SecurityReference("Token Access", authorizationScopes));
    }
}
