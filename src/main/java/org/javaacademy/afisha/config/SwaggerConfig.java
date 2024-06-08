package org.javaacademy.afisha.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server moneyBankServer = new Server();
        moneyBankServer.setUrl("http://localhost:8080");
        moneyBankServer.setDescription("Проект афиша");

        Contact contact = new Contact();
        contact.setEmail("firebolt182@mail.ru");
        contact.setName("Alexander Monaenkov");

        Info info = new Info()
                .title("Проект афиша")
                .version("1.0")
                .contact(contact)
                .description("Апи по проекту афиша");

        return new OpenAPI().info(info).servers(List.of(moneyBankServer));

    }
}
