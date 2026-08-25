package com.petapp.backend.serviceImpl;

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
    public HistorialMedico crear(HistorialMedico historialMedico, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        historialMedico.setMascota(mascota);
        HistorialMedico guardado = historialMedicoRepository.save(historialMedico);
        System.out.println("✅ Historial médico guardado para mascota: " + guardado.getMascota().getNombre());
        return guardado;
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
        List<HistorialMedico> historiales = historialMedicoRepository.findByPropietarioId(propietarioId);
        System.out.println("📋 Registros de historial médico encontrados: " + historiales.size());
        return historiales;
    }

    @Override
    @Transactional
    public HistorialMedico actualizar(Long id, HistorialMedico datosActualizados, Long propietarioId) {
        HistorialMedico existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setFecha(datosActualizados.getFecha());
        existente.setMotivoConsulta(datosActualizados.getMotivoConsulta());
        existente.setDiagnostico(datosActualizados.getDiagnostico());
        existente.setTratamiento(datosActualizados.getTratamiento());
        existente.setVeterinario(datosActualizados.getVeterinario());
        existente.setClinica(datosActualizados.getClinica());

        if (datosActualizados.getMascota() != null && datosActualizados.getMascota().getId() != null) {
            Long nuevaMascotaId = datosActualizados.getMascota().getId();
            Mascota mascota = mascotaRepository.findById(nuevaMascotaId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe una mascota con id " + nuevaMascotaId));

            if (!mascota.getPropietario().getId().equals(propietarioId)) {
                throw new RuntimeException("La mascota no pertenece al usuario autenticado");
            }
            existente.setMascota(mascota);
        }

        return historialMedicoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        HistorialMedico existente = obtenerPorIdYPropietario(id, propietarioId);
        historialMedicoRepository.delete(existente);
    }
}