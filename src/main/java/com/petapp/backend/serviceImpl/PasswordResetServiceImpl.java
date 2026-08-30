package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.ResetPasswordDto;
import com.petapp.backend.model.PasswordResetToken;
import com.petapp.backend.model.Usuario;
import com.petapp.backend.repository.PasswordResetTokenRepository;
import com.petapp.backend.repository.UsuarioRepository;
import com.petapp.backend.service.EmailService;
import com.petapp.backend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    // SIN @Transactional aquí: permite liberar la conexión a la base de datos de inmediato
    public void solicitarRecuperacion(String email) {
        // 1. Ejecuta la consulta y guardado en DB (Abre y CIERRA la conexión al instante)
        String token = crearYGuardarToken(email);

        // 2. Invoca el correo de forma asíncrona FUERA de la transacción de base de datos
        emailService.enviarCorreoRecuperacion(email, token);
    }

    @Transactional // Transactional aislado sólo para el trabajo con MySQL
    public String crearYGuardarToken(String email) {
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún usuario asociado a este correo"));

        // Eliminar tokens previos activos para el mismo usuario
        tokenRepository.deleteByUsuario(usuario);

        // Generar token único válido por 15 minutos
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .usuario(usuario)
                .fechaExpiracion(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(resetToken);
        return token;
    }

    @Override
    @Transactional
    public void restablecerPassword(ResetPasswordDto dto) {
        PasswordResetToken resetToken = tokenRepository.findByToken(dto.token())
                .orElseThrow(() -> new RuntimeException("El token proporcionado es inválido"));

        if (resetToken.isExpirado()) {
            tokenRepository.delete(resetToken);
            throw new RuntimeException("El enlace de recuperación ha expirado. Solicita uno nuevo.");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setContrasenaHash(passwordEncoder.encode(dto.nuevaPassword()));
        usuarioRepository.save(usuario);

        // Eliminar el token una vez utilizado
        tokenRepository.delete(resetToken);
    }
}