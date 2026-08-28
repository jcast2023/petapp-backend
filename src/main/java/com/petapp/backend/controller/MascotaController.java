package com.petapp.backend.controller;

import com.petapp.backend.dto.MascotaRequestDTO;
import com.petapp.backend.dto.MascotaResponseDTO;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@Valid @RequestBody MascotaRequestDTO request) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Mascota creada = mascotaService.crear(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MascotaResponseDTO(creada));
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> listar() {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        List<MascotaResponseDTO> mascotas = mascotaService.listarPorPropietario(usuarioId)
                .stream()
                .map(MascotaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Mascota mascota = mascotaService.obtenerPorIdYPropietario(id, usuarioId);
        return ResponseEntity.ok(new MascotaResponseDTO(mascota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody MascotaRequestDTO request) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Mascota actualizada = mascotaService.actualizar(id, request, usuarioId);
        return ResponseEntity.ok(new MascotaResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        mascotaService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}