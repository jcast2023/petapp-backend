package com.petapp.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    private static final Dotenv dotenv;

    static {
        Dotenv tempDotenv = null;
        try {
            // Intentar cargar .env (desarrollo local)
            tempDotenv = Dotenv.load();
            System.out.println("✅ Archivo .env cargado correctamente (desarrollo local)");
        } catch (Exception e) {
            // Si no existe .env, usar variables de sistema (producción)
            System.out.println("ℹ️ Archivo .env no encontrado. Usando variables de entorno del sistema.");
        }
        dotenv = tempDotenv;
    }

    public static String get(String key) {
        if (dotenv != null) {
            String value = dotenv.get(key);
            if (value != null) {
                return value;
            }
        }
        // Fallback a variables de sistema
        String systemValue = System.getenv(key);
        return systemValue != null ? systemValue : null;
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}