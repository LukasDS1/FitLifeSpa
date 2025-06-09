package com.example.membresia_service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
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

    public List<Membresia> findMembresiasByPlanId(Long idPlan) {
    return membresiaRepository.findByPlan_IdPlan(idPlan);
    }

    public List<Membresia> findbyidUsuario(Long idUsuario){
    return membresiaRepository.findByUsuarioId(idUsuario);
    }

    public Optional<Membresia> getMembresiaById(Long idMembresia) {
        return membresiaRepository.findById(idMembresia);
    }

    public Membresia saveMembresia(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }

    public Boolean deleteMembresia(Long idMembresia) {
        try {
            Optional<Membresia> exist = membresiaRepository.findById(idMembresia);
            if(exist.isPresent()){
                membresiaRepository.deleteById(idMembresia);
                return true;
            }
            System.out.println("Membresia con ID: "+idMembresia+" no existe");
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error interno ");
        }

    }

    public Boolean validarUsuario(Long idUsuario){
        String url = "http://localhost:8082/api-v1/register/exists/{id}";
        try {
            @SuppressWarnings("rawtypes")
            Map objeto = restTemplate.getForObject(url, Map.class, idUsuario);

            if (objeto == null || objeto.isEmpty()) {
                return false;
            }
            return true;

        } catch (HttpClientErrorException.NotFound e) {
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateUser(Long idUsuario) {
    if (idUsuario == null) return false;

    try {
        return validarUsuario(idUsuario);
    } catch (RuntimeException e) {
        return false;
    }
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

    public Membresia assignPlanToMembership(Long idMembresia, Long idPlan) {
        Membresia membresia = membresiaRepository.findById(idMembresia)
                .orElseThrow(() -> new RuntimeException("Membresia con ID:" + idMembresia + " no encontrada."));
        Plan plan = planRepository.findById(idPlan)
                .orElseThrow(() -> new RuntimeException("Plan con ID: " + idPlan + " no encontrado."));
        membresia.setPlan(plan);

        return membresiaRepository.save(membresia);
    }

   public Membresia assignUsuarioToMembership(Long idMembresia, Long idUsuario) {
    Membresia membresia = membresiaRepository.findById(idMembresia).orElseThrow(() -> new RuntimeException("Membresia con ID:" + idMembresia + " no encontrada."));

    if (!validarUsuario(idUsuario)) {
        throw new RuntimeException("El usuario con ID: " + idUsuario + " no existe.");
    }

    if (!membresia.getIdUsuario().contains(idUsuario)) {
        membresia.getIdUsuario().add(idUsuario);
    }
    return membresiaRepository.save(membresia);
    }

}
