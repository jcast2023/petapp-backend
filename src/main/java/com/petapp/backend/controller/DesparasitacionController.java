package com.petapp.backend.controller;

import com.petapp.backend.dto.DesparasitacionRequestDTO;
import com.petapp.backend.dto.DesparasitacionResponseDTO;
import com.petapp.backend.model.Desparasitacion;
import com.petapp.backend.service.DesparasitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/desparasitaciones")
public class DesparasitacionController {

    private final DesparasitacionService desparasitacionService;

    @Autowired
    public DesparasitacionController(DesparasitacionService desparasitacionService) {
        this.desparasitacionService = desparasitacionService;
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
    public ResponseEntity<DesparasitacionResponseDTO> crear(@Valid @RequestBody DesparasitacionRequestDTO request,
                                                            @RequestParam Long mascotaId) {
        Long usuarioId = obtenerUsuarioId();
        Desparasitacion creada = desparasitacionService.crear(request, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DesparasitacionResponseDTO(creada));
    }

    @GetMapping
    public ResponseEntity<List<DesparasitacionResponseDTO>> listar() {
        Long usuarioId = obtenerUsuarioId();
        List<DesparasitacionResponseDTO> lista = desparasitacionService.listarPorPropietario(usuarioId)
                .stream()
                .map(DesparasitacionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesparasitacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        Desparasitacion desparasitacion = desparasitacionService.obtenerPorIdYPropietario(id, usuarioId);
        return ResponseEntity.ok(new DesparasitacionResponseDTO(desparasitacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DesparasitacionResponseDTO> actualizar(@PathVariable Long id,
                                                                 @Valid @RequestBody DesparasitacionRequestDTO request) {
        Long usuarioId = obtenerUsuarioId();
        Desparasitacion actualizada = desparasitacionService.actualizar(id, request, usuarioId);
        return ResponseEntity.ok(new DesparasitacionResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        desparasitacionService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}