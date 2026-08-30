package com.petapp.backend.serviceImpl;

import com.petapp.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarCorreoRecuperacion(String emailDestino, String token) {
        String urlReset = "https://petapp-frontend-k0fr.onrender.com/#/reset-password?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailDestino);
        mensaje.setSubject("PetApp - Restablecer Contraseña");
        mensaje.setText("Hola,\n\nHas solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n\n"
                + urlReset + "\n\nEste enlace expirará en 15 minutos.\n\nSi no realizaste esta solicitud, ignora este mensaje.");

        mailSender.send(mensaje);
    }
}
