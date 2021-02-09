package com.diplom.vrp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.diplom.vrp.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("VRP-Solution API")
                .description("VRP-Solution API as a diploma work")
                .license("Apache 2.0").contact(new Contact("Vlad Bezdsuhnyi",
                        "https://www.linkedin.com/in/vlad-bezdushnii/", "vladibzd@gmail.com"))
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").version("1.0").build();
    }
}
