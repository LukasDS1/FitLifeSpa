package com.example.clase_service.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.clase_service.model.Clase;
import com.example.clase_service.repository.ClaseRepository;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {
    
    @Bean
    CommandLineRunner initDatabase(ClaseRepository claseRepository){
        return args ->{
            if(claseRepository.count() == 0){
                Clase Pilates = new Clase(null,"Clase de pilates",new java.sql.Date(System.currentTimeMillis()),"Clase de pilates",null);
                claseRepository.save(Pilates);

            }else{
                System.out.println("Datos ya existen.No se cargaron");
            }
        };
    }

}
