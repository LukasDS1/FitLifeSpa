package com.example.membresia_service.controller;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.model.Plan;
import com.example.membresia_service.repository.PlanRepository;
import com.example.membresia_service.service.MembresiaService;
import com.example.membresia_service.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MembresiaController.class)

public class MembresiaControllerTest {

    @MockitoBean
    private MembresiaService membresiaService;
    
    @MockitoBean
    private PlanService planService;

    @MockitoBean
    private PlanRepository planRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createmembresia_thenreturncreated() throws Exception{

        Membresia membresia = new Membresia(1L, "membresia1", "membresia1", null, null);

        when(membresiaService.saveMembresia(membresia)).thenReturn(membresia);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(membresia);

        try {
            mockMvc.perform(post("/api-v1/membresia/crearmembresia").contentType(MediaType.APPLICATION_JSON).content(Json))
            .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listallmembresias_thenreturnok(){
        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", null, null),
        new Membresia(2L, "membresia2", "membresia2", null, null));

        when(membresiaService.getByAll()).thenReturn(membresias);

        try {
            mockMvc.perform(get("/api-v1/membresia/listallmembresia")).andExpect(status().isOk()).andExpect(jsonPath( "$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listByIdMembresia_thenreturnMembresiaandOK(){
        Long idMembresia = 1L;
        Membresia membresia = new Membresia(idMembresia, "membresia1", "membresia1", null, null);

        when(membresiaService.findByid(idMembresia)).thenReturn(Optional.of(membresia));

        try {
            mockMvc.perform(get("/api-v1/membresia/listid/{idMembresia}",idMembresia)).andExpect(status().isOk())
            .andExpect(jsonPath("$.idMembresia").value(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void deletemembresiabyID_thenreturnMembresia(){
        Long idMembresia = 1L;

        doReturn(true).when(membresiaService).deleteMembresia(idMembresia);

        try {
            mockMvc.perform(delete("/api-v1/membresia/deletemembresia/{idMembresia}",idMembresia)).andExpect(status().isAccepted())
            .andExpect(content().string("Membresía borrada con exito"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void updatemembresiaById_thenreturnOk() throws Exception{
        Long idMembresia = 1L;

        Membresia membresia = new Membresia(idMembresia, "membresia1", "membresia1", null, null);
        Membresia membresia2 = new Membresia(idMembresia, "membresia2", "membresia1", null, null);

        when(membresiaService.UpdateUserById(membresia)).thenReturn(membresia2);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(membresia);

        try {
            mockMvc.perform(patch("/api-v1/membresia/update/{idMembresia}",idMembresia).contentType(MediaType.APPLICATION_JSON)
            .content(Json)).andExpect(status().isOk()).andExpect(jsonPath( "$.nombre").value("membresia2"));
        } catch (Exception e) {
           e.printStackTrace();
        }
    }



    @Test
    void deleteUserFromMembresia_thenReturnOk() throws Exception {
        Long idMembresia = 1L;
        Long idUsuario = 1L;

        List<Long> usuarios = new ArrayList<>();
        usuarios.add(idUsuario);

        Membresia membresia = new Membresia(idMembresia, "membresia2", "membresia1", usuarios, null);

        when(membresiaService.deleteUsersFromMembresia(idMembresia, idUsuario)).thenReturn(membresia);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(membresia);
        try {
            mockMvc.perform(delete("/api-v1/membresia/deleteuser/{idUser}", idUsuario).contentType(MediaType.APPLICATION_JSON).content(Json))
            .andExpect(status().isOk()).andExpect(content().string("Usuario borrado correctamente de la membresia"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void assignPlantoMembresia_thenReturnAccepted() throws Exception {
        Long idMembresia = 1L;
        Long idPlan = 1L;

        Plan plan = new Plan();
        plan.setIdPlan(idPlan);

        Membresia membresia = new Membresia();
        membresia.setIdMembresia(idMembresia);
        membresia.setPlan(plan);

        when(membresiaService.assignPlanToMembership(idMembresia, idPlan)).thenReturn(membresia);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(membresia);

        try {
            mockMvc.perform(put("/api-v1/membresia/assignplan").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isAccepted()) 
           .andExpect(content().string("Plan con ID: " + idPlan + " agregada con exito a la membresia de ID: " + idMembresia));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void assignUserToMembresia_theReturnAccepted() throws Exception{
        Long idUsuario = 1L;
        Long idMembresia = 1L;
        Membresia membresia = new Membresia();
        membresia.setIdMembresia(idMembresia);

        when(membresiaService.assignUsuarioToMembership(idMembresia, idUsuario)).thenReturn(membresia);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(membresia);


        try {
            mockMvc.perform(put("/api-v1/membresia/assignuser/{idUsuario}",idUsuario).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isAccepted()).andExpect(content().string("Usuario con ID: " + idUsuario + " agregado con éxito a la membresía ID: " + idMembresia));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void listPlanByMembresiaID_ReturnsList(){
        Long idPlan = 1L;
        Plan plan = new Plan();
        plan.setIdPlan(idPlan);

        when(planRepository.findById(idPlan)).thenReturn(Optional.of(plan));
        
        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", null, plan),
        new Membresia(2L, "membresia2", "membresia2", null, plan));

        when(membresiaService.findMembresiasByPlanId(idPlan)).thenReturn(membresias);

        try {
             mockMvc.perform(get("/api-v1/membresia/plan/{idPlan}", idPlan)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void listUserByMembresiaID_returnsList(){
        Long idUsuario = 10L;
        List<Long> usuarios = new ArrayList<>();
        usuarios.add(idUsuario);

        List<Membresia> membresias = Arrays.asList(new Membresia(1L, "membresia1", "membresia1", usuarios, null),
        new Membresia(2L, "membresia2", "membresia2", usuarios, null));

        when(membresiaService.validarUsuario(idUsuario)).thenReturn(true);

        when(membresiaService.findbyidUsuario(idUsuario)).thenReturn(membresias);
        try {
            mockMvc.perform(get("/api-v1/membresia/usuario/{idUsuario}", idUsuario)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}











