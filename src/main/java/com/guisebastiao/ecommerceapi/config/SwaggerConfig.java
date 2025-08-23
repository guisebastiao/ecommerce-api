package com.guisebastiao.ecommerceapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        License license = new License()
                .name("MIT")
                .url("https://github.com/guisebastiao/ecommerce-api/blob/main/LICENSE");

        Contact contact = new Contact()
                .name("Guilherme Sebastião")
                .email("guilhermesebastiaou.u@gmail.com")
                .url("https://github.com/guisebastiao");

        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API")
                        .description("Documentação da API usando Swagger/OpenAPI")
                        .version("1.0")
                        .license(license)
                        .contact(contact))
                .components(new Components()
                        .addSecuritySchemes("access-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                                        .name("access-token")
                        ))
                .components(new Components()
                        .addSecuritySchemes("refresh-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                                        .name("refresh-token")
                        ));
    }
}
