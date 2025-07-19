package br.com.myaccounts.my_finance_account_ms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Finance Account MS API")
                        .description("Sistema de Gestão Financeira Pessoal - API para controle de receitas, despesas e categorias")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Guilherme Araújo")
                                .email("guilherme@myaccounts.com")
                                .url("https://github.com/guilherme-araujo"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://api.myfinance.com")
                                .description("Servidor de Produção")
                ));
    }
}
