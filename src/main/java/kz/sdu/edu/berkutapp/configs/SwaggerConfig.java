package kz.sdu.edu.berkutapp.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        Contact contact = new Contact();
        contact.setEmail("200103394@stu.sdu.edu.kz");
        contact.setName("Moldir Kazbek");
        Info info = new Info().contact(contact)
                .title("Berkut app REST API");
        return new OpenAPI().info(info);
    }
}