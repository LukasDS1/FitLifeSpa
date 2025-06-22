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
                Rol admin = new Rol(null,"Administrador",new ArrayList<>());
                rolRepository.save(admin);

                Rol coordi = new Rol(null, "Coordinador", new ArrayList<>());
                rolRepository.save(coordi);

                Rol ent = new Rol(null, "Entrenador", new ArrayList<>());
                rolRepository.save(ent);

                Rol sop = new Rol(null,"Soporte",new ArrayList<>());
                rolRepository.save(sop);

                Rol cli = new Rol(null,"Cliente",new ArrayList<>());
                rolRepository.save(cli);

                Usuario administrador = new Usuario(
                null,
                "administrador@gmail.com",
                passwordEncoder.encode("1234"),
                "admin",
                "appa1",
                "apma1",
                "Masculino",
                "12345678-1", admin);
                usuarioRepository.save(administrador);

                Usuario coordinador = new Usuario(
                null, 
                "coordinador@gmail.com", 
                passwordEncoder.encode("4123"), 
                "coordinador", 
                "appa2", 
                "apma2", 
                "Femenino", 
                "12344567-2", coordi);
                usuarioRepository.save(coordinador);

                Usuario entrenador = new Usuario(
                null, 
                "entrenador@gmail.com", 
                passwordEncoder.encode("3412"), 
                "entrenador", 
                "appa3", 
                "apma3", 
                "Masculino", 
                "12345678-9", ent);
                usuarioRepository.save(entrenador);

                Usuario soporte = new Usuario(
                null, 
                "soporte@gmail.com", 
                passwordEncoder.encode("2341"), 
                "soporte", 
                "appa4", 
                "apma4", 
                "Femenino", 
                "12345234-1", sop);
                usuarioRepository.save(soporte);

                Usuario user = new Usuario(
                null, 
                "usuario@gmail.com", 
                passwordEncoder.encode("4321"), 
                "usuario", 
                "appa5", 
                "apma5", 
                "Masculino", 
                "12349865-1", cli);
                usuarioRepository.save(user);

                Usuario user2 = new Usuario(
                null, 
                "usuario2@gmail.com", 
                passwordEncoder.encode("1210"), 
                "usuario2", 
                "appa6", 
                "apma6", 
                "Masculino", 
                "87654345-9", cli);
                usuarioRepository.save(user2);

                Usuario user3 = new Usuario(
                null, 
                "usuario3@gmail.com", 
                passwordEncoder.encode("1012"), 
                "usuario3", 
                "appa7", 
                "apma7", 
                "Femenino", 
                "98761234-7", cli);
                usuarioRepository.save(user3);

            }else{
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
    

}
