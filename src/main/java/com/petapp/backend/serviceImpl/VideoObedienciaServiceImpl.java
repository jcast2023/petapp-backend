package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.VideoObedienciaRequestDTO;
import com.petapp.backend.model.VideoObediencia;
import com.petapp.backend.repository.VideoObedienciaRepository;
import com.petapp.backend.service.RecursoNoEncontradoException;
import com.petapp.backend.service.VideoObedienciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoObedienciaServiceImpl implements VideoObedienciaService {

    private final VideoObedienciaRepository videoObedienciaRepository;

    @Autowired
    public VideoObedienciaServiceImpl(VideoObedienciaRepository videoObedienciaRepository) {
        this.videoObedienciaRepository = videoObedienciaRepository;
    }

    @Override
    public VideoObediencia crear(VideoObedienciaRequestDTO request) {
        VideoObediencia video = new VideoObediencia();
        video.setTitulo(request.getTitulo());
        video.setDescripcion(request.getDescripcion());
        video.setUrlVideo(request.getUrlVideo());
        video.setComando(request.getComando());
        video.setNivel(request.getNivel());
        video.setDuracionSegundos(request.getDuracionSegundos());
        video.setMiniaturaUrl(request.getMiniaturaUrl());
        return videoObedienciaRepository.save(video);
    }

    @Override
    public List<VideoObediencia> listarTodos() {
        return videoObedienciaRepository.findAll();
    }

    @Override
    public List<VideoObediencia> listarPorNivel(String nivel) {
        return videoObedienciaRepository.findByNivel(nivel);
    }

    @Override
    public VideoObediencia obtenerPorId(Long id) {
        return videoObedienciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un video con id " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (!videoObedienciaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un video con id " + id);
        }
        videoObedienciaRepository.deleteById(id);
    }
}