package com.petapp.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtener la ruta absoluta del directorio
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String uploadUri = uploadPath.toUri().toString();

        // Mapear la URL publica /uploads/** hacia el directorio del sistema de archivos
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadUri);
    }
}
