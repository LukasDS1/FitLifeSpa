package com.example.membresia_service.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.service.PlanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-v1/plan")
@RequiredArgsConstructor

public class PlanController {

    private final PlanService planService;

    @PostMapping("/crear")
    public ResponseEntity<Plan> savePlan(@RequestBody Plan plan) {
        try {
            planService.CreatePlan(plan);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(plan);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


     @GetMapping("/listall")
    public ResponseEntity<List<Plan>> getAllPlanes() {
        try {
            return ResponseEntity.ok(planService.getAllPlan());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

     @GetMapping("/listid/{idPlan}")
    public ResponseEntity<Plan> getById(@PathVariable Long idPlan) {
        try {
            Optional<Plan> exist = planService.getbyID(idPlan);
            if (exist.isPresent()) {
                return ResponseEntity.ok(exist.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException("Membresia con ID: " +idPlan+ "no encontrada");
        }
    }
  
    @GetMapping("listMembresiaplan{idPlan}")
    public ResponseEntity<List<Membresia>> getMembresiasPorPlan(@PathVariable Long idPlan) {
        List<Membresia> membresias = planService.getMembresiasByPlanId(idPlan);
        return ResponseEntity.ok(membresias);
    }

      @DeleteMapping("/deleteplan/{idPlan}")
    public ResponseEntity<String> deletePlan (@PathVariable Long idplan) {
        try {
  
           if( planService.delete(idplan)){
               return ResponseEntity.ok().body("Plan borrado con exito!");
           }
           return ResponseEntity.badRequest().body("Plan con ID: "+idplan+" no encontrado");
        } catch (Exception e) {
            throw new RuntimeException("ola");
        }
    }

     @PutMapping("/updateplan")
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan plan) {
        try {
            planService.updatePlan(plan);
            return ResponseEntity.ok().body(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}
