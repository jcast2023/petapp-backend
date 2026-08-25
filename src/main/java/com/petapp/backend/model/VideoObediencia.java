package com.petapp.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "videos_obediencia")
public class VideoObediencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "La URL del video es obligatoria")
    @Column(nullable = false)
    private String urlVideo;

    private String comando; // ej. "Sentado", "Quieto", "Ven"

    private String nivel; // "Básico", "Intermedio", "Avanzado"

    private Integer duracionSegundos;

    private String miniaturaUrl;

    public VideoObediencia() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
