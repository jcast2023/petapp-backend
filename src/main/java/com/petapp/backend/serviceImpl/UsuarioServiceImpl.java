package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Usuario;
import com.petapp.backend.repository.UsuarioRepository;
import com.petapp.backend.service.CorreoYaRegistradoException;
import com.petapp.backend.service.CredencialesInvalidasException;
import com.petapp.backend.service.RecursoNoEncontradoException;
import com.petapp.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(String nombre, String correo, String contrasenaPlana) {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new CorreoYaRegistradoException("Ya existe una cuenta con el correo " + correo);
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContrasenaHash(passwordEncoder.encode(contrasenaPlana));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario autenticar(String correo, String contrasenaPlana) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));

        if (!passwordEncoder.matches(contrasenaPlana, usuario.getContrasenaHash())) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }
        return usuario;
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id " + id));
    }
}