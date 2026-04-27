package com.bookstore.notificationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore Microservice — Notification Service")
                        .description("RESTful API documentation — Notification Service. Test email/SMS notification triggers manually without Kafka.")
                        .version("v1.0")
                        .contact(new Contact().name("Dev Team")));
    }
}
