package com.petapp.backend.dto;

public class AuthResponse {

    private Long usuarioId;
    private String nombre;
    private String correo;

    public AuthResponse(Long usuarioId, String nombre, String correo) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}