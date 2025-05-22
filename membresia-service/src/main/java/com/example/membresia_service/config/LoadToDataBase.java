package com.example.membresia_service.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.model.Rol;
import com.example.membresia_service.model.Usuario;
import com.example.membresia_service.repository.MembresiaRepository;
import com.example.membresia_service.repository.PlanRepository;
import com.example.membresia_service.repository.RolRepository;
import com.example.membresia_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDataBase {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository,RolRepository rolRepository, PlanRepository planRepository, MembresiaRepository membresiaRepository){
        return args->{
            if(usuarioRepository.count() == 0 && membresiaRepository.count() == 0 && planRepository.count() == 0 && membresiaRepository.count() == 0 && rolRepository.count() == 0){

                Rol cliente = new Rol(null,"Cliente",new ArrayList<>());
                rolRepository.save(cliente);

                Membresia bronze = new Membresia(null,"Bronze","Membresia de acceso limitado",null,null);
                membresiaRepository.save(bronze);

                Membresia silver = new Membresia(null,"Silver","Membresia con acceso medio",null,null);
                membresiaRepository.save(silver);

                Membresia gold = new Membresia(null,"Gold","Membresia sin limitaciones",null,null);
                membresiaRepository.save(gold);

                Plan premium = new Plan(null, "Premium", "Plan premium", 200000, 365, new ArrayList<>());
                planRepository.save(premium);

                Plan medium = new Plan(null, "Medium", "Plan medium", 1200000, 180, new ArrayList<>());
                planRepository.save(medium);

                Plan low = new Plan(null, "Low", "Plan Low", 20000, 30, new ArrayList<>());
                planRepository.save(low);

                Usuario usuario = new Usuario(null,"Lukas@gmail.com","null","lukas","donoso","ponce","masculino","12345678-9",null,new ArrayList<>());
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setRol(cliente);
                usuarioRepository.save(usuario);

                Usuario usuario2 = new Usuario(null,"benjamin@gmail.com",passwordEncoder.encode("1234"),"benja","moreti","dias","masculino","99876543-1",null,new ArrayList<>());
                usuario2.setRol(cliente);
                usuarioRepository.save(usuario2);

                bronze.setPlan(low);
                membresiaRepository.save(bronze);

                silver.setPlan(medium);
                membresiaRepository.save(silver);

                gold.setPlan(premium);
                membresiaRepository.save(gold);
                
                bronze.setUsuario(usuario);
                membresiaRepository.save(bronze);
                
                bronze.setUsuario(usuario2);
                membresiaRepository.save(bronze);

                usuario.getMembresia().add(bronze);
                usuarioRepository.save(usuario);
                usuario2.getMembresia().add(bronze);
                usuarioRepository.save(usuario2);

            } else{
                System.out.println("datos existentes, no se incertaron");
            }

        };
    }

}
