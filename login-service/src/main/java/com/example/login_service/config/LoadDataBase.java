package com.example.login_service.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.login_service.model.Rol;
import com.example.login_service.model.Usuario;
import com.example.login_service.repository.RolRepository;
import com.example.login_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDataBase(RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        return args -> {
            if (rolRepository.count() == 0 && usuarioRepository.count() == 0) {
                Rol admin = new Rol(null,"Administrador", new ArrayList<>());
                //admin.setNombre("Administrador");
                rolRepository.save(admin);

                Rol cliente = new Rol(null, "Cliente", new ArrayList<>());
                rolRepository.save(cliente);

                Usuario usuario1 = new Usuario();
                usuario1.setEmail("nuevo@ejemplo.com");
                usuario1.setPassword(passwordEncoder.encode("claveSegura123"));
                usuario1.setNombre("Carlos");
                usuario1.setApellidoPaterno("Ramírez");
                usuario1.setApellidoMaterno("Díaz");
                usuario1.setGenero("Masculino");
                usuario1.setRut("12345678-9");
                usuario1.setRol(cliente);
                usuarioRepository.save(usuario1);
            } else {
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
}
