// O nome do pacote deve ser o mesmo do seu projeto, seguindo a estrutura de pastas.
// Por exemplo, se a sua pasta é com/apiNicoly/chatbot, o pacote deve ser com.apiNicoly.chatbot.
package com.apiNicoly.chatbot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// A anotação @Configuration indica que esta classe é uma fonte de definições de beans.
@Configuration
public class CorsConfig {

    // O método anotado com @Bean cria uma instância de WebMvcConfigurer que será gerenciada pelo Spring.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configura as regras de CORS para o seu aplicativo.
                registry.addMapping("/**") // Aplica a configuração a todos os endpoints ("todas as rotas").
                        .allowedOrigins("*") // Permite requisições de qualquer origem ("permite qualquer front"). Para produção, é recomendado restringir a URLs específicas.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite os métodos HTTP especificados.
                        .allowedHeaders("*"); // Permite todos os cabeçalhos nas requisições.
            }
        };
    }
}