package com.petapp.backend.controller;

import com.petapp.backend.model.Desparasitacion;
import com.petapp.backend.service.DesparasitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Desparasitacion> crear(@Valid @RequestBody Desparasitacion desparasitacion) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🆕 Creando desparasitación para usuario: " + usuarioId);

        Long mascotaId = desparasitacion.getMascota().getId();
        System.out.println("🐾 Mascota ID: " + mascotaId);
        System.out.println("💊 Producto: " + desparasitacion.getProducto());
        System.out.println("📋 Tipo: " + desparasitacion.getTipo());

        Desparasitacion creada = desparasitacionService.crear(desparasitacion, mascotaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<Desparasitacion>> listar() {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("📋 Listando desparasitaciones para usuario: " + usuarioId);
        List<Desparasitacion> lista = desparasitacionService.listarPorPropietario(usuarioId);
        System.out.println("📊 Desparasitaciones encontradas: " + lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desparasitacion> obtenerPorId(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🔍 Obteniendo desparasitación ID: " + id);
        return ResponseEntity.ok(desparasitacionService.obtenerPorIdYPropietario(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Desparasitacion> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody Desparasitacion datosActualizados) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("✏️ Actualizando desparasitación ID: " + id);
        return ResponseEntity.ok(desparasitacionService.actualizar(id, datosActualizados, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long usuarioId = obtenerUsuarioId();
        System.out.println("🗑️ Eliminando desparasitación ID: " + id);
        desparasitacionService.eliminar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}