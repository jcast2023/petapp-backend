package com.petapp.backend.controller;

import com.petapp.backend.dto.VacunaRequestDTO;
import com.petapp.backend.dto.VacunaResponseDTO;
import com.petapp.backend.model.Vacuna;
import com.petapp.backend.service.VacunaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    @Autowired
    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    @PostMapping
    public ResponseEntity<VacunaResponseDTO> crear(@Valid @RequestBody VacunaRequestDTO request,
                                                   @RequestParam Long mascotaId) {
        Long propietarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Vacuna creada = vacunaService.crear(request, mascotaId, propietarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new VacunaResponseDTO(creada));
    }

    @GetMapping
    public ResponseEntity<List<VacunaResponseDTO>> listar() {
        Long propietarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        List<VacunaResponseDTO> vacunas = vacunaService.listarPorPropietario(propietarioId)
                .stream()
                .map(VacunaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vacunas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacunaResponseDTO> obtenerPorId(@PathVariable Long id) {
        Long propietarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Vacuna vacuna = vacunaService.obtenerPorIdYPropietario(id, propietarioId);
        return ResponseEntity.ok(new VacunaResponseDTO(vacuna));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacunaResponseDTO> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody VacunaRequestDTO request) {
        Long propietarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        Vacuna actualizada = vacunaService.actualizar(id, request, propietarioId);
        return ResponseEntity.ok(new VacunaResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Long propietarioId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        vacunaService.eliminar(id, propietarioId);
        return ResponseEntity.noContent().build();
    }
}