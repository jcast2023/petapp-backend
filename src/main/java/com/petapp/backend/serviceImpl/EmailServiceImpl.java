package com.petapp.backend.serviceImpl;

import com.petapp.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${BREVO_API_KEY:xkeysib-sin-key}")
    private String apiKey;

    @Value("${BREVO_SENDER_EMAIL:tu.correo@gmail.com}")
    private String senderEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Async
    public void enviarCorreoRecuperacion(String emailDestino, String token) {
        String urlApi = "https://api.brevo.com/v3/smtp/email";

        // 1. Headers HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        // 2. Construir cuerpo JSON del request
        String urlReset = "https://petapp-frontend-k0fr.onrender.com/#/reset-password?token=" + token;

        String htmlBody = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                + "<h2>Recuperación de Contraseña</h2>"
                + "<p>Hemos recibido una solicitud para restablecer tu contraseña en <strong>PetApp</strong>.</p>"
                + "<p>Haz clic en el siguiente botón para continuar:</p>"
                + "<a href='" + urlReset + "' style='background-color: #4F46E5; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Restablecer Contraseña</a>"
                + "<p style='margin-top: 20px; color: #666;'>Si no solicitaste este cambio, puedes ignorar este mensaje.</p>"
                + "</div>";

        Map<String, Object> body = new HashMap<>();

        // Sender
        Map<String, String> sender = new HashMap<>();
        sender.put("name", "PetApp Support");
        sender.put("email", senderEmail);
        body.put("sender", sender);

        // To (Destinatario)
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", emailDestino);
        body.put("to", Collections.singletonList(recipient));

        // Asunto y Contenido HTML
        body.put("subject", "Restablecer Contraseña - PetApp");
        body.put("htmlContent", htmlBody);

        // 3. Enviar la petición HTTP POST
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(urlApi, request, String.class);
            System.out.println("Correo enviado con éxito a: " + emailDestino);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo vía Brevo REST API: " + e.getMessage());
        }
    }
}