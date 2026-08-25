package com.petapp.backend.controller;

import com.petapp.backend.model.VideoObediencia;
import com.petapp.backend.service.VideoObedienciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos-obediencia")
public class VideoObedienciaController {

    private final VideoObedienciaService videoObedienciaService;

    @Autowired
    public VideoObedienciaController(VideoObedienciaService videoObedienciaService) {
        this.videoObedienciaService = videoObedienciaService;
    }

    @PostMapping
    public ResponseEntity<VideoObediencia> crear(@Valid @RequestBody VideoObediencia video) {
        VideoObediencia creado = videoObedienciaService.crear(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<VideoObediencia>> listarTodos() {
        return ResponseEntity.ok(videoObedienciaService.listarTodos());
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<VideoObediencia>> listarPorNivel(@PathVariable String nivel) {
        return ResponseEntity.ok(videoObedienciaService.listarPorNivel(nivel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoObediencia> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videoObedienciaService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        videoObedienciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
