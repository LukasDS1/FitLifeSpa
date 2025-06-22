package com.example.soporteservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.service.MotivoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MotivoController.class)
public class MotivoControllerTest {


    @MockitoBean
    private MotivoService motivoService;


    @Autowired
    private MockMvc mockMvc;


    @Test
    void createMotivo_returnsCratedAndCreatedMotivo() throws Exception{

        Motivo motivo = new Motivo(1L, "Enojo y Desesperacion", null);

        when(motivoService.createMotivo(motivo)).thenReturn(motivo);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(motivo);
        

        try {
            mockMvc.perform(post("/api-v1/motivo/crearmotivo").contentType(MediaType.APPLICATION_JSON).content(Json))
            .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void listarMotivoByID_returnsListMotivo(){

        Long idMotivo = 1L;

        Motivo motivo = new Motivo(idMotivo, "Enojo y Desesperacion", null);

        when(motivoService.getMotivo(idMotivo)).thenReturn(Optional.of(motivo));

        try {
            mockMvc.perform(get("/api-v1/motivo/listarmotivo/{idMotivo}",idMotivo)).andExpect(status()
            .isOk()).andExpect(jsonPath( "$.idMotivo").value(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void listarAllMotivos_returnsOk(){

        List<Motivo> motivos = Arrays.asList(new Motivo(1L, "Dudas", null),
        new Motivo(2L, "Enojo y Desesperacion", null));

        when(motivoService.getAllMotivo()).thenReturn(motivos);

        try {
            mockMvc.perform(get("/api-v1/motivo/listarmotivo")).andExpect(status().isOk()).andExpect(jsonPath( "$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
