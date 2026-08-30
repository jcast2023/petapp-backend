package com.petapp.backend.service;

import com.petapp.backend.dto.ResetPasswordDto;

public interface PasswordResetService {
    void solicitarRecuperacion(String email);
    void restablecerPassword(ResetPasswordDto dto);
}
