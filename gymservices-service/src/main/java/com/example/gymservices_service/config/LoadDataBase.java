package com.example.gymservices_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(ServicioRepository servicioRepository){
        return args ->{
            if(servicioRepository.count() == 0){

                Servicio nutricion = new Servicio(null,"Asesoría nutricional","Asesioria nutricional");
                servicioRepository.save(nutricion);

                Servicio spa = new Servicio(null,"SPA","SPA");
                servicioRepository.save(spa);

                Servicio grupal = new Servicio(null,"Clases Grupales","Clases Grupales");
                servicioRepository.save(grupal);

                Servicio evaluacion = new Servicio(null,"Evaluacion fisica","Evaluacion fisica");
                servicioRepository.save(evaluacion);

                Servicio guarderia = new Servicio(null,"Guarderia","Zona infantil");
                servicioRepository.save(guarderia);


               

            }else{
                System.out.println("Datos ya existen.No se cargaron");
            }
        };
    }

}
