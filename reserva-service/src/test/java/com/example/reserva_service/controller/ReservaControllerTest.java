package com.example.reserva_service.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.example.reserva_service.config.TestSecurityConfig;
import com.example.reserva_service.model.EstadoReserva;
import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.service.ReservaService;
import com.example.reserva_service.service.estadoReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReservaController.class)
@Import(TestSecurityConfig.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService reservaService;

    @MockitoBean
    private estadoReservaService estadoReservaService;


    @Test
    void testFindAll_ReturnsOkWithReservas() throws Exception {

        List<Reserva> reservas = List.of(new Reserva(), new Reserva());

        when(reservaService.listarReservas()).thenReturn(reservas);

        mockMvc.perform(get("/api-v1/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }



    @Test
    void testFindByIdReserva_Found() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(1L);

        when(reservaService.buscarPorId(1L)).thenReturn(reserva);

        mockMvc.perform(get("/api-v1/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReserva").value(1));
    }

    @Test
    void testAddReserva_Created() throws Exception {
        Reserva reserva = new Reserva(1L, new Date(), new Date(), "desc", 1L, 1L, 1L, null);

        when(reservaService.agregarReserva(any(Reserva.class))).thenReturn(reserva);

            mockMvc.perform(post("/api-v1/reservas/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(reserva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReserva").value(1));
    }


    @Test
    void findByidUser_returnsList_whenUserHasReservas() throws Exception {
        List<Reserva> reservas = List.of(new Reserva());
        when(reservaService.listarPorIdUser(1L)).thenReturn(reservas);

        mockMvc.perform(get("/api-v1/reservas/usuario/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void updateReserva_returnsUpdatedReserva_whenIdExists() throws Exception {
        Reserva reservaOriginal = new Reserva(1L, new Date(), new Date(), "original", 1L, 1L, 1L, null);
        Reserva reservaActualizada = new Reserva(null, new Date(), new Date(), "nueva", 2L, 2L, 1L, null);

        when(reservaService.buscarPorId(1L)).thenReturn(reservaOriginal);
        when(reservaService.agregarReserva(any(Reserva.class))).thenReturn(reservaOriginal);

        mockMvc.perform(put("/api-v1/reservas/update/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(reservaActualizada)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descripcion").value("nueva"));
    }

    @Test
    void deleteReserva_returnsOk_whenReservaExists() throws Exception {
    Reserva reserva = new Reserva();
        when(reservaService.buscarPorId(1L)).thenReturn(reserva);

        mockMvc.perform(delete("/api-v1/reservas/delete/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Reserva eliminada"));

        verify(reservaService).borrarReserva(1L);
    }

    @Test
    void deleteEstadoReserva_returnsOk_whenEstadoExists() throws Exception {
        EstadoReserva estado = new EstadoReserva();
        when(estadoReservaService.listarPorId(1L)).thenReturn(estado);
        estado.setIdEstado(1L);

        mockMvc.perform(delete("/api-v1/reservas/estados/delete/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Estado Eliminado"));

        verify(estadoReservaService).eliminarEstadoReserva(1L);
    }
}

