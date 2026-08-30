package com.petapp.backend.controller;

import com.petapp.backend.dto.ResetPasswordDto;
import com.petapp.backend.dto.SolicitudRecuperacionDto;
import com.petapp.backend.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/recuperar-password")
    public ResponseEntity<Map<String, String>> solicitarRecuperacion(@Valid @RequestBody SolicitudRecuperacionDto dto) {
        passwordResetService.solicitarRecuperacion(dto.email());
        return ResponseEntity.ok(Map.of("mensaje", "Se ha enviado un enlace de recuperación a tu correo electrónico"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> restablecerPassword(@Valid @RequestBody ResetPasswordDto dto) {
        passwordResetService.restablecerPassword(dto);
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente"));
    }
}