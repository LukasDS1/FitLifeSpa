package com.example.soporteservice.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TicketController.class)

public class TicketControllerTest {

    @MockitoBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createTicket_returnsCREATEDandExpectString() throws Exception{

        Motivo motivo = new Motivo();
        motivo.setIdMotivo(1L);


        List<Historial> historial = Arrays.asList(new Historial(1L, "prueba1", "prueba1", new Date(), null));
        Ticket ticket = new Ticket(1L, new Date(), 1L, 1L, 1l, historial, motivo);
       

        when(ticketService.validarUsuario(anyLong())).thenReturn(true);
        when(ticketService.validarEstado(anyLong())).thenReturn(true);
        
        when(ticketService.createTicket(ticket)).thenReturn(ticket);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(ticket);

        try {
            mockMvc.perform(post("/api-v1/ticket/creartk").contentType(MediaType.APPLICATION_JSON).content(Json))
            .andExpect(status().isCreated()).andExpect(content().string("El ticket ha sido creado con exito"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void listAllTks_returnListaAllTks(){
        Motivo motivo = new Motivo();
        motivo.setIdMotivo(1L);

        List<Ticket> tickets = Arrays.asList(new Ticket(1L, new Date(), 1L, 1L, 1l, null, motivo),
        new Ticket(2L, new Date(), 1L, 1L, 1l, null, motivo));

        when(ticketService.getAllTicket()).thenReturn(tickets);

        try {
            mockMvc.perform(get("/api-v1/ticket/listartks")).andExpect(status().isOk()).andExpect(jsonPath( "$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void listTicketsByID_returnsTicketID(){

        Motivo motivo = new Motivo();
        motivo.setIdMotivo(1L);

        Long idTicket = 1L;
        Ticket ticket = new Ticket(idTicket, new Date(), 1L, 1L, 1l, null, motivo);

        when(ticketService.getById(idTicket)).thenReturn(Optional.of(ticket));

        try {
            mockMvc.perform(get("/api-v1/ticket/listartkid/{idTicket}",idTicket)).andExpect(status().isOk())
            .andExpect(jsonPath( "$.idTicket").value(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void getEstado_returnActivo()  {
        Long idTicket = 1L;
        
        Mockito.when(ticketService.obtenerEstadoDelTicket(idTicket)).thenReturn("Activo");
        try {
            mockMvc.perform(get("/api-v1/ticket/estado/{idTicket}", idTicket)).andExpect(status().isOk()).andExpect(content().string("Activo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}
