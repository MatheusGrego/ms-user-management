package com.payment.usermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Permite qualquer origem
                .allowedMethods("*")  // Permite todos os métodos HTTP
                .allowedHeaders("*")  // Permite todos os cabeçalhos
                .allowCredentials(true)  // Desativa envio de credenciais (cookies, etc)
                .maxAge(3600);  // Tempo em segundos que a resposta pode ser armazenada no cache
    }
}
