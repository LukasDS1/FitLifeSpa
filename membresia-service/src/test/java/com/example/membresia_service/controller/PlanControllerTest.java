package com.example.membresia_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PlanController.class)

public class PlanControllerTest {

    @MockitoBean
    private PlanService planService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createPlan_returnsCREATED() throws Exception{
            Plan plan = new Plan(1L, "plan1", "plan1", 3333, 3333, null);

            when(planService.CreatePlan(any(Plan.class))).thenReturn(plan);

            ObjectMapper objectMapper = new ObjectMapper();
            String Json = objectMapper.writeValueAsString(plan);
            
            try {
                mockMvc.perform(post("/api-v1/plan/crear").contentType(MediaType.APPLICATION_JSON).content(Json)).andExpect(status().isCreated());
            } catch (Exception e) {
                e.printStackTrace();
            }
   
        }


    @Test
    void listallplan_returnsplanandSTATUSCODEOK(){

        List<Plan> planes = Arrays.asList(new Plan(1L, "plan1", "plan1", 3333, 3333, null),
        new Plan(2L, "plan2", "plan2", 4444, 4444, null));

        when(planService.getAllPlan()).thenReturn(planes);

        try {
            mockMvc.perform(get("/api-v1/plan/listall")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listPlanByID_returnsOK(){
       Long idPlan = 1L;
       Plan plan =  new Plan(idPlan, "plan1", "plan1", 3333, 3333, null);

        when(planService.getbyID(idPlan)).thenReturn(Optional.of(plan));

        try {
            mockMvc.perform(get("/api-v1/plan/listid/{idPlan}",idPlan)).andExpect(status().isOk()).andExpect(jsonPath("$.idPlan").value(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listmembresiasByplan_returnsOk(){
       Long idPlan = 1L;
       Long idMembresia = 2L;

       Membresia membresia = new Membresia(idMembresia, "membresia1", "membresia1", null, null);

       List<Membresia> membresias = new ArrayList<>();
       membresias.add(membresia);
    
       Plan plan =  new Plan(idPlan, "plan1", "plan1", 3333, 3333, membresias);


       when(planService.getbyID(idPlan)).thenReturn(Optional.of(plan));
       when(planService.getMembresiasByPlanId(idPlan)).thenReturn(membresias);

       try {
        mockMvc.perform(get("/api-v1/plan/listMembresiaplan/{idPlan}",idPlan).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].idMembresia").value(2L)).andExpect(jsonPath("$[0].nombre").value("membresia1"));
       } catch (Exception e) {
        e.printStackTrace();
       }
    }


    @Test
    void deletePlanbyID_returnsOk(){
        Long idPlan = 1L;

        doNothing().when(planService).delete(idPlan);

        try {
            mockMvc.perform(delete("/api-v1/plan/deleteplan/{idPlan}",idPlan)).andExpect(status().isOk())
            .andExpect(content().string("Plan borrado con exito"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void updatePlanByID_returnsUpdatedPlanandOk() throws Exception {

        Long idPlan = 1L; 

        Plan plan = new Plan(idPlan, "plan1", "plan1", 3333, 3333, null);

        Plan plan2 = new Plan(idPlan, "plan2", "plan1", 3333, 3333, null);

        when(planService.updatePlan(plan)).thenReturn(plan2);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(plan);

        try {
            mockMvc.perform(patch("/api-v1/plan/updateplan/{idPlan}",idPlan).contentType(MediaType.APPLICATION_JSON)
            .content(Json)).andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("plan2"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
