package com.example.clase_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.clase_service.model.Clase;
import com.example.clase_service.service.ClaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClaseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClaseService claseService;

    private Clase sampleClase;

    @BeforeEach
    void setUp() {
        sampleClase = new Clase(1L, "Clase 1", new Date(), "Descripción de prueba", 1L);
    }

    @Test
    void getAllClass_returns200() throws Exception {
        List<Clase> clases = List.of(sampleClase);
        when(claseService.getAllClases()).thenReturn(clases);

        mockMvc.perform(get("/api-v1/clase/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Clase 1"));
    }

    @Test
    void createClass_returns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        when(claseService.saveClase(any(Clase.class))).thenReturn(sampleClase);

        mockMvc.perform(post("/api-v1/clase/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sampleClase)))
            .andExpect(status().isOk())
            .andExpect(content().string("clase creada correctamente."));
    }

    @Test
    void getClaseById_returnsClaseWhenFound() throws Exception {
        when(claseService.getClaseById(1L)).thenReturn(Optional.of(sampleClase));

        mockMvc.perform(get("/api-v1/clase/listarid/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Clase 1"));
    }

    @Test
    void deleteClaseById_successful() throws Exception {
        when(claseService.getClaseById(1L)).thenReturn(Optional.of(sampleClase));

        mockMvc.perform(delete("/api-v1/clase/borrar/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Clase con ID: 1 Ha sido borrada con exito"));
    }

    @Test
    void updateClass_successful() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        when(claseService.getClaseById(1L)).thenReturn(Optional.of(sampleClase));
        when(claseService.updateClase(any(Long.class), any(Clase.class))).thenReturn(sampleClase);

        mockMvc.perform(put("/api-v1/clase/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sampleClase)))
            .andExpect(status().isOk())
            .andExpect(content().string("Clase con ID: 1 actualizada con exito"));
    }

    @Test
    void getServicioDeClase_successful() throws Exception {
        Map<String, Object> servicioMock = new HashMap<>();
        servicioMock.put("nombre", "Servicio de prueba");

        when(claseService.obtenerServicioDeClase(1L)).thenReturn(servicioMock);

        mockMvc.perform(get("/api-v1/clase/servicio/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Servicio de prueba"));
    }
}
