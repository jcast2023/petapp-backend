package com.petapp.backend.controller;

import com.petapp.backend.dto.BanoRequestDTO;
import com.petapp.backend.dto.BanoResponseDTO;
import com.petapp.backend.model.Bano;
import com.petapp.backend.service.BanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banos")
public class BanoController {

    private final BanoService banoService;

    @Autowired
    public BanoController(BanoService banoService) {
        this.banoService = banoService;
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
    public ResponseEntity<BanoResponseDTO> crear(@Valid @RequestBody BanoRequestDTO request,
                                                 @RequestParam Long mascotaId) {
        Long usuarioId = obtenerUsuarioId();
        Bano creado = banoService.crear(request, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BanoResponseDTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<BanoResponseDTO>> listar() {
        Long usuarioId = obtenerUsuarioId();
        List<BanoResponseDTO> banos = banoService.listarPorPropietario(usuarioId)
                .stream()
                .map(BanoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(banos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BanoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        Bano bano = banoService.obtenerPorIdYPropietario(id, usuarioId);
        return ResponseEntity.ok(new BanoResponseDTO(bano));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BanoResponseDTO> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody BanoRequestDTO request) {
        Long usuarioId = obtenerUsuarioId();
        Bano actualizado = banoService.actualizar(id, request, usuarioId);
        return ResponseEntity.ok(new BanoResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        banoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}