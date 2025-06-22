package com.example.membresia_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.PlanRepository;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanService planService;

    //Aqui verificamos que al momento de listar todos los usuarios el comportamiento del services sea igual que el del repositorio
    @Test
    void findAllPlan_ReturnsList(){
        List<Plan> planes = Arrays.asList(new Plan(1L, "plan1", "plan1", 3333, 3333, null),
        new Plan(2L, "plan2", "plan2", 4444, 4444, null));

        when(planRepository.findAll()).thenReturn(planes);

        List<Plan> resultado = planService.getAllPlan();

        assertThat(resultado).isEqualTo(planes);
    }

    //Aqui verificamos que cuando busque por el id efectivamente encuentre el usuario
    //se usa el contains enves de isqualto debido a que el metodo getbyid al ser un OPTIONAL puede o no ser nulo, ademas de esto Plan y el Optional no son el mismo tipo de objetos
    @Test
    void findPlanById_returnsPlan(){
        Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));

        Optional<Plan> resultado = planService.getbyID(1L);

        assertThat(resultado).contains(plan);
    }


    //Aqui verificamos que cuando se cree un plan efectivamente se guarde
    @Test
    void createPlan_savesCreatedPlan(){
        Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        Plan savedplan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        when(planRepository.save(any(Plan.class))).thenReturn(savedplan);

        Plan result = planService.CreatePlan(plan);

        assertThat(result).isEqualTo(savedplan);
    }

    //Aqui verificamos que se actualize el plan
    @Test
    void updatePlanById_returnsUpdatePlan(){
        Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));

        Plan updatedplan = new Plan(1L, "plan2", "plan2", 3333, 3333, null);
        
        when(planRepository.save(any(Plan.class))).thenReturn(updatedplan); // simulamos el comportamiento
        
        Plan result = planService.updatePlan(updatedplan);

        verify(planRepository).save(any(Plan.class)); //verificamos que ha sido llamada para guardar el usuario basicamente la interraccion

        assertEquals("plan2", result.getNombre());   // vemos que los resultados sean correctos
        assertEquals("plan2", result.getDescripcion()); // vemos que los resultados sean correctos
    }

    //Aqui verificamos que se borre el plan 
    @Test   
    void deletedPlanById_ItsInvoke(){
        Long idPlan = 1L;

        Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

        when(planRepository.findById(idPlan)).thenReturn(Optional.of(plan));

        planService.delete(idPlan);

        verify(planRepository).delete(plan);
    }

    //Aqui verificamos que se listen las membresias por id plan 
    @Test
    void getMembresiasByPlanId_returnsMembresias() {
    Long idPlan = 1L;
    List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", null, null),
    new Membresia(2L, "membresia2", "membresia2", null, null));

    Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, membresias);

    when(planRepository.findById(idPlan)).thenReturn(Optional.of(plan));

    List<Membresia> resultado = planService.getMembresiasByPlanId(idPlan);

    assertThat(resultado).isEqualTo(membresias);
}







}
