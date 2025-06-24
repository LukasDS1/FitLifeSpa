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
                Clase Pilates = new Clase(null,"Clase de pilates",new java.sql.Timestamp(System.currentTimeMillis()),"Clase de pilates",1L);
                claseRepository.save(Pilates);
                Clase Spinning = new Clase(null,"Clase de Spinnig",new java.sql.Timestamp(System.currentTimeMillis()),"Clase de spinning",1L);
                claseRepository.save(Spinning);
                Clase Zumba = new Clase(null,"Clase de Zumba",new java.sql.Timestamp(System.currentTimeMillis()),"Clase de Zumba",1L);
                claseRepository.save(Zumba);
                Clase CrossFit = new Clase(null,"Clase de Cross Fit",new java.sql.Timestamp(System.currentTimeMillis()),"Clase de Cross Fit",1L);
                claseRepository.save(CrossFit);
                Clase Yoga = new Clase(null,"Clase de Yoga",new java.sql.Timestamp(System.currentTimeMillis()),"Clase de Yoga",1L);
                claseRepository.save(Yoga);
            }else{
                System.out.println("Datos ya existen.No se cargaron");
            }
        };
    }

}
