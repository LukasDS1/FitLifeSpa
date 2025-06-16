package com.example.register_service.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.register_service.dto.RegisterRequestDto;
import com.example.register_service.model.AuthResponse;
import com.example.register_service.model.Rol;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.RolRepository;
import com.example.register_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
// Esta clase se encarga de la autenticación, el registro y la generación de JWT.

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequestDto request) {
        if (request == null) {
            throw new RuntimeException("La información no puede ser nula.");
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario ya existe.");
        }

        Optional<Rol> rol = rolRepository.findById(request.getRol().getIdRol());

        if (rol.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Rol no existe.");
        }

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .genero(request.getGenero())
                .rut(request.getRut())
                .rol(rol.get())
                .build();

        usuarioRepository.save(usuario);

        return AuthResponse.builder()
            .token(jwtService.getToken(usuario))
            .build();
    }

}
