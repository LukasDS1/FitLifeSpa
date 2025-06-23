package com.example.gymservices_service.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.service.ServicioService;

@WebMvcTest(ServicioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioService servicioService;

    private Servicio sampleServicio;

    @BeforeEach
    void setUp() {
        sampleServicio = new Servicio(1L, "Spa", "Servicio de relajación");
    }

    @Test
    void allservices_returns200() throws Exception {
        when(servicioService.allServices()).thenReturn(List.of(sampleServicio));

        mockMvc.perform(get("/api-v1/service/listartodos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Spa")));
    }

    @Test
    void existsById_returnsServicio_whenExists() throws Exception {
        when(servicioService.serviceById(1L)).thenReturn(sampleServicio);

        mockMvc.perform(get("/api-v1/service/exists/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Spa"));
    }

    @Test
    void createService_returns201_whenValid() throws Exception {
        when(servicioService.addService(any())).thenReturn(sampleServicio);

        String json = """
            {
              "idServicio": 1,
              "nombre": "Spa",
              "descripcion": "Servicio de relajación"
            }
        """;

        mockMvc.perform(post("/api-v1/service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Spa"));
    }

    @Test
    void deleteById_returns204_whenDeleted() throws Exception {
        when(servicioService.deleteService(1L)).thenReturn(true);

        mockMvc.perform(delete("/api-v1/service/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateService_returns200_whenUpdated() throws Exception {
        when(servicioService.updateService(eq(1L), any())).thenReturn(sampleServicio);

        String json = """
            {
              "nombre": "Spa",
              "descripcion": "Servicio de relajación"
            }
        """;

        mockMvc.perform(put("/api-v1/service/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Spa"));
    }
}
