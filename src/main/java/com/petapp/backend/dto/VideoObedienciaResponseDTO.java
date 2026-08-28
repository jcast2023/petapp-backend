package com.petapp.backend.dto;

import com.petapp.backend.model.VideoObediencia;

public class VideoObedienciaResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private String comando;
    private String nivel;
    private Integer duracionSegundos;
    private String miniaturaUrl;

    public VideoObedienciaResponseDTO(VideoObediencia video) {
        this.id = video.getId();
        this.titulo = video.getTitulo();
        this.descripcion = video.getDescripcion();
        this.urlVideo = video.getUrlVideo();
        this.comando = video.getComando();
        this.nivel = video.getNivel();
        this.duracionSegundos = video.getDuracionSegundos();
        this.miniaturaUrl = video.getMiniaturaUrl();
    }

    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getUrlVideo() { return urlVideo; }
    public String getComando() { return comando; }
    public String getNivel() { return nivel; }
    public Integer getDuracionSegundos() { return duracionSegundos; }
    public String getMiniaturaUrl() { return miniaturaUrl; }
}
