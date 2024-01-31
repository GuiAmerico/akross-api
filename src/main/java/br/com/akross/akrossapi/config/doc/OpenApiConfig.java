package br.com.akross.akrossapi.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("API AKROSS")
        .version("v0.1")
        .description(
          "Não fazemos por fazer. Fazemos para evoluir, potencializar e acelerar o crescimento."
        )
        .contact(contact())
      );

  }

  private Contact contact() {
    Contact contact = new Contact();
    contact.setEmail("agencia@fontemidia.com.br");
    contact.setName("Akross");
    contact.setUrl("https://www.akross.com.br/pt/contact/");
    return contact;
  }

}