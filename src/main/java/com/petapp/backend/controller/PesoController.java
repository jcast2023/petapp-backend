package com.petapp.backend.controller;

import com.petapp.backend.dto.PesoRequestDTO;
import com.petapp.backend.dto.PesoResponseDTO;
import com.petapp.backend.model.Peso;
import com.petapp.backend.service.PesoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pesos")
public class PesoController {

    private final PesoService pesoService;

    @Autowired
    public PesoController(PesoService pesoService) {
        this.pesoService = pesoService;
    }

    private Long obtenerUsuarioId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        Object credentials = authentication.getCredentials();
        if (credentials instanceof Long) {
            return (Long) credentials;
        }
        throw new RuntimeException("No se pudo obtener el ID del usuario");
    }

    @PostMapping
    public ResponseEntity<PesoResponseDTO> crear(@Valid @RequestBody PesoRequestDTO request,
                                                 @RequestParam Long mascotaId) {
        Long usuarioId = obtenerUsuarioId();
        Peso creado = pesoService.crear(request, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PesoResponseDTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<PesoResponseDTO>> listar() {
        Long usuarioId = obtenerUsuarioId();
        List<PesoResponseDTO> lista = pesoService.listarPorPropietario(usuarioId)
                .stream()
                .map(PesoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PesoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        Peso peso = pesoService.obtenerPorIdYPropietario(id, usuarioId);
        return ResponseEntity.ok(new PesoResponseDTO(peso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PesoResponseDTO> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody PesoRequestDTO request) {
        Long usuarioId = obtenerUsuarioId();
        Peso actualizado = pesoService.actualizar(id, request, usuarioId);
        return ResponseEntity.ok(new PesoResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        pesoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}