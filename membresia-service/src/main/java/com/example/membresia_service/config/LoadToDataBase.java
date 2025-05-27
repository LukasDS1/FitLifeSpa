package com.example.membresia_service.config;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.MembresiaRepository;
import com.example.membresia_service.repository.PlanRepository;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDataBase {



    @Bean
    CommandLineRunner initDatabase(PlanRepository planRepository, MembresiaRepository membresiaRepository){
        return args->{
            if( membresiaRepository.count() == 0 && planRepository.count() == 0){

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

                bronze.setPlan(low);
                membresiaRepository.save(bronze);

                silver.setPlan(medium);
                membresiaRepository.save(silver);

                gold.setPlan(premium);
                membresiaRepository.save(gold);


            } else{
                System.out.println("datos existentes, no se incertaron");
            }

        };
    }

}
