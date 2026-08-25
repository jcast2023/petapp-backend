package com.petapp.backend.controller;

import com.petapp.backend.model.Vacuna;
import com.petapp.backend.service.VacunaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    @Autowired
    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
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
    public ResponseEntity<Vacuna> crear(@Valid @RequestBody Map<String, Object> payload) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🆕 Creando vacuna para usuario: " + usuarioId);
        System.out.println("📦 Payload recibido: " + payload);

        // Extraer campos del payload
        String nombreVacuna = (String) payload.get("nombreVacuna");
        String fechaAplicacion = (String) payload.get("fechaAplicacion");
        String fechaProximaDosis = (String) payload.get("fechaProximaDosis");
        String veterinario = (String) payload.get("veterinario");
        String notas = (String) payload.get("notas");
        Long mascotaId = payload.get("mascotaId") != null ?
                Long.valueOf(payload.get("mascotaId").toString()) : null;

        System.out.println("🐾 Mascota ID: " + mascotaId);
        System.out.println("💉 Vacuna: " + nombreVacuna);

        if (mascotaId == null) {
            throw new RuntimeException("mascotaId es requerido");
        }

        // Crear objeto Vacuna
        Vacuna vacuna = new Vacuna();
        vacuna.setNombreVacuna(nombreVacuna);
        vacuna.setFechaAplicacion(java.time.LocalDate.parse(fechaAplicacion));
        vacuna.setFechaProximaDosis(java.time.LocalDate.parse(fechaProximaDosis));
        vacuna.setVeterinario(veterinario);
        vacuna.setNotas(notas);

        Vacuna creado = vacunaService.crear(vacuna, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Vacuna>> listar() {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("📋 Listando vacunas para usuario: " + usuarioId);
        return ResponseEntity.ok(vacunaService.listarPorPropietario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        return ResponseEntity.ok(vacunaService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacuna> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody Map<String, Object> payload) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("✏️ Actualizando vacuna ID: " + id);

        // Extraer campos del payload
        String nombreVacuna = (String) payload.get("nombreVacuna");
        String fechaAplicacion = (String) payload.get("fechaAplicacion");
        String fechaProximaDosis = (String) payload.get("fechaProximaDosis");
        String veterinario = (String) payload.get("veterinario");
        String notas = (String) payload.get("notas");
        Long mascotaId = payload.get("mascotaId") != null ?
                Long.valueOf(payload.get("mascotaId").toString()) : null;

        // Crear objeto Vacuna
        Vacuna vacuna = new Vacuna();
        vacuna.setNombreVacuna(nombreVacuna);
        vacuna.setFechaAplicacion(java.time.LocalDate.parse(fechaAplicacion));
        vacuna.setFechaProximaDosis(java.time.LocalDate.parse(fechaProximaDosis));
        vacuna.setVeterinario(veterinario);
        vacuna.setNotas(notas);

        if (mascotaId != null) {
            com.petapp.backend.model.Mascota mascota = new com.petapp.backend.model.Mascota();
            mascota.setId(mascotaId);
            vacuna.setMascota(mascota);
        }

        return ResponseEntity.ok(vacunaService.actualizar(id, vacuna, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        vacunaService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}