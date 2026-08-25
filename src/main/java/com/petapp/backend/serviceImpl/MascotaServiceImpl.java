package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Mascota;
import com.petapp.backend.model.Usuario;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.repository.UsuarioRepository;
import com.petapp.backend.service.MascotaService;
import com.petapp.backend.service.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MascotaServiceImpl(MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Mascota crear(Mascota mascota, Long propietarioId) {
        Usuario propietario = usuarioRepository.findById(propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un usuario con id " + propietarioId));
        mascota.setPropietario(propietario);
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + id));
    }

    @Override
    public Mascota obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return mascotaRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + id + " para este usuario"));
    }

    @Override
    public List<Mascota> listarPorPropietario(Long propietarioId) {
        return mascotaRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Mascota actualizar(Long id, Mascota datosActualizados, Long propietarioId) {
        // Verificar que la mascota pertenece al usuario
        Mascota existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setNombre(datosActualizados.getNombre());
        existente.setEspecie(datosActualizados.getEspecie());
        existente.setRaza(datosActualizados.getRaza());
        existente.setFechaNacimiento(datosActualizados.getFechaNacimiento());
        existente.setSexo(datosActualizados.getSexo());
        existente.setFotoUrl(datosActualizados.getFotoUrl());
        return mascotaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        // Verificar que la mascota pertenece al usuario
        Mascota existente = obtenerPorIdYPropietario(id, propietarioId);
        mascotaRepository.delete(existente);
    }
}