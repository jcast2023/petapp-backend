package com.petapp.backend.controller;

import com.petapp.backend.config.JwtUtil;
import com.petapp.backend.dto.AuthResponse;
import com.petapp.backend.dto.LoginRequest;
import com.petapp.backend.dto.RegistroRequest;
import com.petapp.backend.model.Usuario;
import com.petapp.backend.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @Value("${app.cookie-secure}")
    private boolean cookieSecure;

    @Value("${jwt.expiracion-ms}")
    private long expiracionMs;

    @Autowired
    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request,
                                                  HttpServletResponse response) {
        Usuario usuario = usuarioService.registrar(
                request.getNombre(), request.getCorreo(), request.getContrasena());

        establecerCookieJwt(usuario.getId(), usuario.getCorreo(), response);
        AuthResponse respuesta = new AuthResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreo());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        Usuario usuario = usuarioService.autenticar(request.getCorreo(), request.getContrasena());

        establecerCookieJwt(usuario.getId(), usuario.getCorreo(), response);
        AuthResponse respuesta = new AuthResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreo());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> obtenerUsuarioActual(@RequestAttribute("usuarioId") Long usuarioId,
                                                             @RequestAttribute("correo") String correo) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        return ResponseEntity.ok(new AuthResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreo()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookieVacia = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookieVacia.toString());
        return ResponseEntity.noContent().build();
    }

    private void establecerCookieJwt(Long usuarioId, String correo, HttpServletResponse response) {
        String token = jwtUtil.generarToken(usuarioId, correo);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("None")
                .path("/")
                .maxAge(expiracionMs / 1000)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}