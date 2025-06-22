package com.example.membresia_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.PlanRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> getAllPlan(){
        return planRepository.findAll();
    }

    public Optional<Plan> getbyID(Long idPLan){
        return planRepository.findById(idPLan);
    }

    public Plan CreatePlan(Plan plan){
        return planRepository.save(plan);
    }

    public Plan updatePlan(Plan plan) {
        Plan plan2 = planRepository.findById(plan.getIdPlan())
                .orElseThrow(() -> new EntityNotFoundException("Plan inexistente"));

        if (plan.getNombre() != null && !plan.getNombre().trim().isEmpty()) {
            plan2.setNombre(plan.getNombre());
        }

        if (plan.getDescripcion() != null && !plan.getDescripcion().trim().isEmpty()) {
            plan2.setDescripcion(plan.getDescripcion());
        }

        if (plan.getCosto() != null) {
            plan2.setCosto(plan.getCosto());
        }

        if (plan.getDuracion() != null) {
            plan2.setDuracion(plan.getDuracion());
        }

        if (plan.getMembresia() != null && !plan.getMembresia().isEmpty()) {
            for (Membresia m : plan.getMembresia()) {
                m.setPlan(plan2); 
            }
            plan2.setMembresia(plan.getMembresia()); 
        }
        return planRepository.save(plan2);
    }

    public void delete (Long idPlan){
       Plan plan2 = planRepository.findById(idPlan).orElseThrow(() -> new EntityNotFoundException("Plan inexistente"));
       planRepository.delete(plan2);
    }

    public List<Membresia> getMembresiasByPlanId(Long idPlan) {
        Plan plan = planRepository.findById(idPlan)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Plan no encontrado con ID: " + idPlan));

        List<Membresia> membresias = plan.getMembresia();
        return membresias;
    }


}
