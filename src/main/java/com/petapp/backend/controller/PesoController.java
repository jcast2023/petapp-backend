package com.petapp.backend.controller;

import com.petapp.backend.model.Peso;
import com.petapp.backend.service.PesoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Peso> crear(@Valid @RequestBody Peso peso) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🆕 Creando registro de peso para usuario: " + usuarioId);

        Long mascotaId = peso.getMascota().getId();
        System.out.println("🐾 Mascota ID: " + mascotaId);
        System.out.println("⚖️ Peso: " + peso.getPesoKg() + " kg");

        Peso creado = pesoService.crear(peso, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Peso>> listar() {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("📋 Listando registros de peso para usuario: " + usuarioId);
        List<Peso> lista = pesoService.listarPorPropietario(usuarioId);
        System.out.println("📊 Registros encontrados: " + lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peso> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🔍 Obteniendo registro de peso ID: " + id);
        return ResponseEntity.ok(pesoService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peso> actualizar(@PathVariable Long id,
                                           @Valid @RequestBody Peso datosActualizados) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("✏️ Actualizando registro de peso ID: " + id);
        return ResponseEntity.ok(pesoService.actualizar(id, datosActualizados, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🗑️ Eliminando registro de peso ID: " + id);
        pesoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
