package com.petapp.backend.controller;

import com.petapp.backend.model.Mascota;
import com.petapp.backend.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<Mascota> crear(@Valid @RequestBody Mascota mascota) {
        // Obtener el usuario autenticado del SecurityContext
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();

        Mascota creada = mascotaService.crear(mascota, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listar() {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();

        return ResponseEntity.ok(mascotaService.listarPorPropietario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();

        return ResponseEntity.ok(mascotaService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Long id,
                                              @Valid @RequestBody Mascota datosActualizados) {
        Long usuarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();

        return ResponseEntity.ok(mascotaService.actualizar(id, datosActualizados, usuarioId));
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