package com.example.register_service.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.register_service.model.Rol;
import com.example.register_service.model.Usuario;
import com.example.register_service.repository.RolRepository;
import com.example.register_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository, UsuarioRepository usuarioRepository){
        return args->{
            if(rolRepository.count() == 0 && usuarioRepository.count() == 0 ){
                Rol admin = new Rol(null,"Admin",new ArrayList<>());
                rolRepository.save(admin);
                Rol cliente = new Rol(null,"Cliente",new ArrayList<>());
                rolRepository.save(cliente);
                Rol Soporte = new Rol(null,"Soporte",new ArrayList<>());
                rolRepository.save(Soporte);

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
            }else{
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
    

}
