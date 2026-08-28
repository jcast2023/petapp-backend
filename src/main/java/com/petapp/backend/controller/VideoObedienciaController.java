package com.petapp.backend.controller;

import com.petapp.backend.dto.VideoObedienciaRequestDTO;
import com.petapp.backend.dto.VideoObedienciaResponseDTO;
import com.petapp.backend.model.VideoObediencia;
import com.petapp.backend.service.VideoObedienciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videos-obediencia")
public class VideoObedienciaController {

    private final VideoObedienciaService videoObedienciaService;

    @Autowired
    public VideoObedienciaController(VideoObedienciaService videoObedienciaService) {
        this.videoObedienciaService = videoObedienciaService;
    }

    @PostMapping
    public ResponseEntity<VideoObedienciaResponseDTO> crear(@Valid @RequestBody VideoObedienciaRequestDTO request) {
        VideoObediencia creado = videoObedienciaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new VideoObedienciaResponseDTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<VideoObedienciaResponseDTO>> listarTodos() {
        List<VideoObedienciaResponseDTO> lista = videoObedienciaService.listarTodos()
                .stream()
                .map(VideoObedienciaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<VideoObedienciaResponseDTO>> listarPorNivel(@PathVariable String nivel) {
        List<VideoObedienciaResponseDTO> lista = videoObedienciaService.listarPorNivel(nivel)
                .stream()
                .map(VideoObedienciaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoObedienciaResponseDTO> obtenerPorId(@PathVariable Long id) {
        VideoObediencia video = videoObedienciaService.obtenerPorId(id);
        return ResponseEntity.ok(new VideoObedienciaResponseDTO(video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        videoObedienciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}