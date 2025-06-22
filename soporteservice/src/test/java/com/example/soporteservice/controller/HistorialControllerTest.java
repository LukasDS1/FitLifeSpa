package com.example.soporteservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.HistorialService;
import com.example.soporteservice.service.TicketService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(HistorialController.class)
public class HistorialControllerTest {
    
    @MockitoBean
    private HistorialService historialService;

    @MockitoBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void listHistoriales_returnsOKAndHistoriales(){
        List<Historial> historials = Arrays.asList(new Historial(1L, "Historial 1", "Enojo", new Date(), null),
        new Historial(2L, "Historial 2", "Felicidad", new Date(), null));
        
        when(historialService.getHistorial()).thenReturn(historials);

        try {
            mockMvc.perform(get("/api-v1/historial/listarhistorial")).andExpect(status().isOk()).andExpect(jsonPath( "$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listHistorialByID_returnsOk(){
        Long idHistorial = 1L;
        Historial historial = new Historial(idHistorial, "Historial 1", "Enojo", new Date(), null);

        when(historialService.getHistorialById(idHistorial)).thenReturn(historial);

        try {
            mockMvc.perform(get("/api-v1/historial/listarhistorial/{idHistorial}",idHistorial)).andExpect(status().isOk())
            .andExpect(jsonPath( "$.idHistorial").value(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }

    
    @Test
    void listhistorialbyIDTicket_thenreturnHistorial(){
        Long idTicket = 1L;
        Ticket ticket = new Ticket();
        ticket.setIdTicket(idTicket);

        List<Historial> historial = Arrays.asList(new Historial(1L, "Historial 1", "Enojo", new Date(), ticket),
        new Historial(1L, "Historial 1", "Enojo", new Date(), ticket));

        when(ticketService.getById(idTicket)).thenReturn(Optional.of(ticket));

        when(historialService.getHistorialesByTicketId(idTicket)).thenReturn(historial);

        try {
            mockMvc.perform(get("/api-v1/historial/ticket/{idTicket}",idTicket)).andExpect(status().isOk())
            .andExpect(jsonPath( "$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
