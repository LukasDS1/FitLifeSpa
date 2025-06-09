package com.example.membresia_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.PlanRepository;

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

    public Plan updatePlan(Plan plan){
        Optional<Plan> exist = planRepository.findById(plan.getIdPlan());
        if(exist.isEmpty()){
            throw new RuntimeException("ID plan no puede ser nulo");
        }
        Plan plan2 = exist.get();
        plan2.setNombre(plan.getNombre());
        plan2.setCosto(plan.getCosto());
        plan2.setDescripcion(plan.getDescripcion());
        plan2.setDuracion(plan.getDuracion());
        plan2.setMembresia(plan.getMembresia());
        return planRepository.save(plan2);
    }

    public Boolean delete(Long idPlan) {
        Optional<Plan> exist = planRepository.findById(idPlan);
        try {
            if (exist.isEmpty()) {
                return false;
            } else {
                planRepository.deleteById(idPlan);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("");
        }

    }

    public List<Membresia> getMembresiasByPlanId(Long idPlan) {
        Plan plan = planRepository.findById(idPlan)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + idPlan));

        List<Membresia> membresias = plan.getMembresia();
        return membresias;
    }

}
