package com.petapp.backend.controller;

import com.petapp.backend.model.Bano;
import com.petapp.backend.service.BanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Bano> crear(@Valid @RequestBody Bano bano) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🆕 Creando baño para usuario: " + usuarioId);

        // Obtener mascotaId del body
        Long mascotaId = bano.getMascota().getId();
        System.out.println("🐾 Mascota ID: " + mascotaId);

        Bano creado = banoService.crear(bano, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Bano>> listar() {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("📋 Listando baños para usuario: " + usuarioId);
        List<Bano> banos = banoService.listarPorPropietario(usuarioId);
        System.out.println("📊 Baños encontrados: " + banos.size());
        return ResponseEntity.ok(banos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bano> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🔍 Obteniendo baño ID: " + id + " para usuario: " + usuarioId);
        return ResponseEntity.ok(banoService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bano> actualizar(@PathVariable Long id,
                                           @Valid @RequestBody Bano datosActualizados) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("✏️ Actualizando baño ID: " + id + " para usuario: " + usuarioId);
        return ResponseEntity.ok(banoService.actualizar(id, datosActualizados, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🗑️ Eliminando baño ID: " + id + " para usuario: " + usuarioId);
        banoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}