package com.petapp.backend.controller;

import com.petapp.backend.dto.HistorialMedicoRequestDTO;
import com.petapp.backend.dto.HistorialMedicoResponseDTO;
import com.petapp.backend.model.HistorialMedico;
import com.petapp.backend.service.HistorialMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historial-medico")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
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
    public ResponseEntity<HistorialMedicoResponseDTO> crear(@Valid @RequestBody HistorialMedicoRequestDTO request,
                                                            @RequestParam Long mascotaId) {
        Long usuarioId = obtenerUsuarioId();
        HistorialMedico creado = historialMedicoService.crear(request, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new HistorialMedicoResponseDTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<HistorialMedicoResponseDTO>> listar() {
        Long usuarioId = obtenerUsuarioId();
        List<HistorialMedicoResponseDTO> lista = historialMedicoService.listarPorPropietario(usuarioId)
                .stream()
                .map(HistorialMedicoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        HistorialMedico historial = historialMedicoService.obtenerPorIdYPropietario(id, usuarioId);
        return ResponseEntity.ok(new HistorialMedicoResponseDTO(historial));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedicoResponseDTO> actualizar(@PathVariable Long id,
                                                                 @Valid @RequestBody HistorialMedicoRequestDTO request) {
        Long usuarioId = obtenerUsuarioId();
        HistorialMedico actualizado = historialMedicoService.actualizar(id, request, usuarioId);
        return ResponseEntity.ok(new HistorialMedicoResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        historialMedicoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}