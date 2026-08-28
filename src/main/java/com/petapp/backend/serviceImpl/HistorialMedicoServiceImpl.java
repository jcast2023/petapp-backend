package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.HistorialMedicoRequestDTO;
import com.petapp.backend.model.HistorialMedico;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.repository.HistorialMedicoRepository;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.service.HistorialMedicoService;
import com.petapp.backend.service.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistorialMedicoServiceImpl implements HistorialMedicoService {

    private final HistorialMedicoRepository historialMedicoRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public HistorialMedicoServiceImpl(HistorialMedicoRepository historialMedicoRepository,
                                      MascotaRepository mascotaRepository) {
        this.historialMedicoRepository = historialMedicoRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    @Transactional
    public HistorialMedico crear(HistorialMedicoRequestDTO request, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        HistorialMedico historial = new HistorialMedico();
        historial.setFecha(request.getFecha());
        historial.setMotivoConsulta(request.getMotivoConsulta());
        historial.setDiagnostico(request.getDiagnostico());
        historial.setTratamiento(request.getTratamiento());
        historial.setVeterinario(request.getVeterinario());
        historial.setClinica(request.getClinica());
        historial.setMascota(mascota);
        return historialMedicoRepository.save(historial);
    }

    @Override
    public HistorialMedico obtenerPorId(Long id) {
        return historialMedicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un registro de historial médico con id " + id));
    }

    @Override
    public HistorialMedico obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return historialMedicoRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un registro de historial médico con id " + id + " para este usuario"));
    }

    @Override
    public List<HistorialMedico> listarPorPropietario(Long propietarioId) {
        return historialMedicoRepository.findByPropietarioId(propietarioId);
    }

    @Override
    @Transactional
    public HistorialMedico actualizar(Long id, HistorialMedicoRequestDTO request, Long propietarioId) {
        HistorialMedico existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setFecha(request.getFecha());
        existente.setMotivoConsulta(request.getMotivoConsulta());
        existente.setDiagnostico(request.getDiagnostico());
        existente.setTratamiento(request.getTratamiento());
        existente.setVeterinario(request.getVeterinario());
        existente.setClinica(request.getClinica());
        return historialMedicoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        HistorialMedico existente = obtenerPorIdYPropietario(id, propietarioId);
        historialMedicoRepository.delete(existente);
    }
}