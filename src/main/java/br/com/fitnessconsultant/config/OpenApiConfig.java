package br.com.fitnessconsultant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${api.openapi.dev-url}")
    private String devUrl;

    @Value("${api.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("joaocarlos.cic1@gmail.com");
        contact.setName("João Carlos");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Consultor Fitness API")
                .version("1.0")
                .contact(contact)
                .description("Esta API expõe os endpoints para gerenciamento de clientes e seus treinos de um consultor fitness.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer,prodServer));
    }
}
