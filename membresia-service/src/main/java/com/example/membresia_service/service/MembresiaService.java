package com.example.membresia_service.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.model.Usuario;
import com.example.membresia_service.repository.MembresiaRepository;
import com.example.membresia_service.repository.PlanRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final RestTemplate restTemplate;
    private final PlanRepository planRepository;

    public List<Membresia> getByAll() {
        return membresiaRepository.findAll();
    }

    public Optional<Membresia> findByid(Long idMembresia) {
        return membresiaRepository.findById(idMembresia);

    }
 //todosacar:::
    public Optional<Membresia> getMembresiaById(Long idMembresia) {
        return membresiaRepository.findById(idMembresia);
    }

    public Membresia saveMembresia(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }

    public Boolean deleteMembresia(Long idMembresia) {
        Optional<Membresia> exist = membresiaRepository.findById(idMembresia);
        try {
            if (exist.isEmpty()) {
                return false;
            } else {
                membresiaRepository.deleteById(idMembresia);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("");
        }

    }

    public Usuario exist(String email) {
        Usuario usuarioRequest = new Usuario();
        usuarioRequest.setEmail(email);

        String url_register_service = "http://localhost:8082/api-v1/register/exists";

        try {
            Usuario Usuarioexist = restTemplate.postForObject(url_register_service, usuarioRequest, Usuario.class);
            System.out.println("Usuario encontrado: " + Usuarioexist);
            return Usuarioexist;
        } catch (HttpClientErrorException e) {
            System.out.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del usuario" + e.getMessage());
        }
    }

    public boolean validateUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        Usuario usuario1 = exist(email);
        return usuario1 != null;
    }

    public Membresia updatMembresia(Membresia membresia) {
        Optional<Membresia> exist = membresiaRepository.findById(membresia.getIdMembresia());
        if (exist.isEmpty()) {
            throw new RuntimeException();
        } else {
            Membresia membresia2 = exist.get();
            membresia2.getIdMembresia();
            membresia2.setNombre(membresia.getNombre());
            membresia2.setDescripcion(membresia.getDescripcion());
            membresia2.setPlan(membresia.getPlan());
            return membresiaRepository.save(membresia2);
        }

    }


    public Membresia assignPlanToMembership(Long idMembresia,Long idPlan){
        Membresia membresia = membresiaRepository.findById(idMembresia).orElseThrow(()-> new RuntimeException("Membresia con ID:"+idMembresia+" no encontrada."));
        Plan plan = planRepository.findById(idPlan).orElseThrow(()-> new RuntimeException("Plan con ID: "+idPlan+" no encontrado."));
        membresia.setPlan(plan);

        return membresiaRepository.save(membresia);
    }


    public Membresia assignUsuarioToMembership(Long idMembresia,String email){
        Membresia membresia = membresiaRepository.findById(idMembresia).orElseThrow(()-> new RuntimeException("Membresia con ID:"+idMembresia+" no encontrada."));
        Usuario exist = exist(email);
        if(exist == null){
            throw new RuntimeException("Usuario con Email: "+email+" no encontrado.");
        }
        membresia.setUsuario(exist);
        return membresiaRepository.save(membresia);
    }

}
