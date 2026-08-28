package com.petapp.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret;
    private final long expiracionMs;

    public JwtUtil() {
        this.secret = System.getenv().getOrDefault("JWT_SECRET", "miClaveSecretaSuperSeguraParaJWT2026");
        this.expiracionMs = Long.parseLong(System.getenv().getOrDefault("JWT_EXPIRACION_MS", "86400000"));
    }

    private SecretKey getClave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(Long usuarioId, String correo) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiracionMs);

        return Jwts.builder()
                .subject(correo)
                .claim("usuarioId", usuarioId)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(getClave())
                .compact();
    }

    public String extraerCorreo(String token) {
        return parsearClaims(token).getSubject();
    }

    public Long extraerUsuarioId(String token) {
        return parsearClaims(token).get("usuarioId", Long.class);
    }

    public boolean esTokenValido(String token) {
        try {
            parsearClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(getClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}