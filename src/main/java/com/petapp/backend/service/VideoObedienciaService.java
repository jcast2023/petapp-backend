package com.petapp.backend.service;

import com.petapp.backend.model.VideoObediencia;

import java.util.List;

public interface VideoObedienciaService {
    VideoObediencia crear(VideoObediencia video);
    List<VideoObediencia> listarTodos();
    List<VideoObediencia> listarPorNivel(String nivel);
    VideoObediencia obtenerPorId(Long id);
    void eliminar(Long id);
}
