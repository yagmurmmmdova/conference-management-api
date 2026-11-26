package com.example.springmentorlesson21.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Conference Management API",
                version = "1.0.0",
                description = "RESTful API for managing conference speakers, talks, business rule validations, and automated scheduling.",
                contact = @Contact(name = "Yagmur Memmedova")
        )
)
public class OpenApiConfig {
}