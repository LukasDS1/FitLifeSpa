package com.example.resena_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.resena_service.config.SecurityConfig;
import com.example.resena_service.model.Resenia;
import com.example.resena_service.service.ReseniaService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(ReseniaController.class)
@Import(SecurityConfig.class) // o TestSecurityConfig para permitir todo
public class ReseniaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReseniaService reseniaService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void findAllResenias_returnsOkWithList() throws Exception {
        List<Resenia> lista = List.of(new Resenia(), new Resenia());

        when(reseniaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api-v1/resenias/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findById_returnsResenia_whenExists() throws Exception {
        Resenia resenia = new Resenia();
        resenia.setIdResenia(1L);

        when(reseniaService.buscarId(1L)).thenReturn(resenia);

        mockMvc.perform(get("/api-v1/resenias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idResenia").value(1));
    }

    @Test
    void addResenia_returnsCreated_whenValid() throws Exception {
        Resenia resenia = new Resenia();
        resenia.setIdUsuario(1L);
        resenia.setIdServicio(2L);
        resenia.setDescripcion("Buena clase");

        when(reseniaService.agregarResenia(any(Resenia.class))).thenReturn(resenia);

        mockMvc.perform(post("/api-v1/resenias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.idServicio").value(2));
    }

    @Test
    void deleteResenia_returnsOk_whenExists() throws Exception {
        Resenia resenia = new Resenia();
        resenia.setIdResenia(1L);

        when(reseniaService.buscarId(1L)).thenReturn(resenia);

        mockMvc.perform(delete("/api-v1/resenias/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("El contenido se elimino correctamente"));

        verify(reseniaService).Eliminar(1L);
    }

    @Test
    void findReseniasByIdServicio_returnsOkWithList() throws Exception {
        List<Resenia> lista = List.of(new Resenia(), new Resenia());

        Long testID = 1L;
        when(reseniaService.buscarPorIdServicio(testID)).thenReturn(lista);

        mockMvc.perform(get("/api-v1/resenias/servicio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
