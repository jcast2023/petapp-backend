package com.petapp.backend.controller;

import com.petapp.backend.model.HistorialMedico;
import com.petapp.backend.service.HistorialMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<HistorialMedico> crear(@Valid @RequestBody HistorialMedico historialMedico) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🆕 Creando historial médico para usuario: " + usuarioId);

        Long mascotaId = historialMedico.getMascota().getId();
        System.out.println("🐾 Mascota ID: " + mascotaId);
        System.out.println("🏥 Motivo: " + historialMedico.getMotivoConsulta());

        HistorialMedico creado = historialMedicoService.crear(historialMedico, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listar() {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("📋 Listando historial médico para usuario: " + usuarioId);
        List<HistorialMedico> lista = historialMedicoService.listarPorPropietario(usuarioId);
        System.out.println("📊 Registros encontrados: " + lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🔍 Obteniendo historial ID: " + id);
        return ResponseEntity.ok(historialMedicoService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody HistorialMedico datosActualizados) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("✏️ Actualizando historial ID: " + id);
        return ResponseEntity.ok(historialMedicoService.actualizar(id, datosActualizados, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🗑️ Eliminando historial ID: " + id);
        historialMedicoService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}