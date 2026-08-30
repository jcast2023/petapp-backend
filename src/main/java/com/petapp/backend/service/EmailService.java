package com.petapp.backend.service;

public interface EmailService {
    void enviarCorreoRecuperacion(String emailDestino, String token);
}