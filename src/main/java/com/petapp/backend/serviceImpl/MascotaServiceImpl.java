package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.MascotaRequestDTO;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.model.Usuario;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.service.MascotaService;
import com.petapp.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public MascotaServiceImpl(MascotaRepository mascotaRepository, UsuarioService usuarioService) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Mascota crear(MascotaRequestDTO request, Long usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setSexo(request.getSexo());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setFotoUrl(request.getFotoUrl());
        mascota.setPropietario(usuario);
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));
    }

    @Override
    public Mascota obtenerPorIdYPropietario(Long id, Long propietarioId) {
        Mascota mascota = obtenerPorId(id);
        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para esta mascota");
        }
        return mascota;
    }

    @Override
    public List<Mascota> listarPorPropietario(Long propietarioId) {
        return mascotaRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Mascota actualizar(Long id, MascotaRequestDTO request, Long usuarioId) {
        Mascota mascota = obtenerPorIdYPropietario(id, usuarioId);
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setSexo(request.getSexo());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setFotoUrl(request.getFotoUrl());
        return mascotaRepository.save(mascota);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Mascota mascota = obtenerPorIdYPropietario(id, propietarioId);
        mascotaRepository.delete(mascota);
    }
}