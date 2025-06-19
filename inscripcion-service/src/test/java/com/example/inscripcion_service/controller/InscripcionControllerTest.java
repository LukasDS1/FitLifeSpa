package com.example.inscripcion_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.inscripcion_service.config.SecurityConfig;
import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.model.Estado;
import com.example.inscripcion_service.model.Inscripcion;
import com.example.inscripcion_service.service.ClaseService;
import com.example.inscripcion_service.service.EstadoService;
import com.example.inscripcion_service.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(InscripcionController.class)
@Import(SecurityConfig.class)
public class InscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InscripcionService inscriService;

    @MockitoBean
    private EstadoService estadoService;

    @MockitoBean
    private ClaseService claseService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void allInscripciones_returnsOkWithList() throws Exception {
        List<Inscripcion> lista = List.of(new Inscripcion(), new Inscripcion());
        when(inscriService.listarInscripcion()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripciones/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findByIdUser_returnsOkWithList() throws Exception {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscriService.listarPorUsuario(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripciones/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void findByIdClass_returnsOkWithList() throws Exception {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscriService.listarIncripcionesPorClase(5L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripciones/clase/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listByStatus_returnsOkWithList() throws Exception {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscriService.listarIncripcionesPorEstado(3L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inscripciones/estado/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void addInscripcion_returnsCreated_whenValid() throws Exception {
        Inscripcion inscripcion = new Inscripcion();
        Estado estado = new Estado();
        Clase clase = new Clase();
        inscripcion.setEstado(estado);
        inscripcion.setClase(clase);

        when(estadoService.validarEstado(estado)).thenReturn(true);
        when(claseService.validarClase(clase)).thenReturn(true);

        when(inscriService.agragarInscripcion(any(Inscripcion.class))).thenReturn(inscripcion);

        mockMvc.perform(post("/api/v1/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").exists())
                .andExpect(jsonPath("$.clase").exists());
    }


    @Test
    void findInscById_returnsOk_whenExists() throws Exception {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdInscripcion(10L);

        when(inscriService.validacion(10L)).thenReturn(true);
        when(inscriService.buscarporId(10L)).thenReturn(inscripcion);

        mockMvc.perform(get("/api/v1/inscripciones/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInscripcion").value(10));
    }

    @Test
    void deleteInscripcion_returnsNoContent_whenExists() throws Exception {
        when(inscriService.validacion(15L)).thenReturn(true);
        doNothing().when(inscriService).eliminarInscripcion(15L);

        mockMvc.perform(delete("/api/v1/inscripciones/15"))
                .andExpect(status().isNoContent());

        verify(inscriService).eliminarInscripcion(15L);
    }

    @Test
    void updateInscripcion_returnsOk_whenSuccess() throws Exception {
        Inscripcion inscripcionUpdate = new Inscripcion();
        inscripcionUpdate.setFechaInscripcion(new Date());
        inscripcionUpdate.setClase(new Clase());
        inscripcionUpdate.setEstado(new Estado());

        when(inscriService.agragarInscripcion(any(Inscripcion.class))).thenReturn(inscripcionUpdate);

        mockMvc.perform(put("/api/v1/inscripciones/20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcionUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInscripcion").value(20));
    }



}

