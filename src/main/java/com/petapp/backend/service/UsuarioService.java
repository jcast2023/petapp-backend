package com.petapp.backend.service;

import com.petapp.backend.model.Usuario;

public interface UsuarioService {
    Usuario registrar(String nombre, String correo, String contrasenaPlana);
    Usuario autenticar(String correo, String contrasenaPlana);
    Usuario obtenerPorId(Long id);
}
