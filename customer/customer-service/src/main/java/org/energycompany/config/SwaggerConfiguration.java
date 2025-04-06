package org.energycompany.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String TITLE = "Customer Service API";
    private static final String DESCRIPTION = "API for managing customers";
    private static final String VERSION = "1.0.0";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION);
    }
}
