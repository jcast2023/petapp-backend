package com.petapp.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class VideoObedienciaRequestDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "La URL del video es obligatoria")
    private String urlVideo;

    private String comando;
    private String nivel;
    private Integer duracionSegundos;
    private String miniaturaUrl;

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlVideo() { return urlVideo; }
    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }

    public String getComando() { return comando; }
    public void setComando(String comando) { this.comando = comando; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getDuracionSegundos() { return duracionSegundos; }
    public void setDuracionSegundos(Integer duracionSegundos) { this.duracionSegundos = duracionSegundos; }

    public String getMiniaturaUrl() { return miniaturaUrl; }
    public void setMiniaturaUrl(String miniaturaUrl) { this.miniaturaUrl = miniaturaUrl; }
}
