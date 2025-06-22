package com.example.soporteservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    @InjectMocks
    private TicketService ticketService;

    @Test
    void listTickets_returnAlltickets(){
        List<Ticket> tickets = Arrays.asList(new Ticket(1L, new Date(), null, null, null, null, null),
        new Ticket(2L, new Date(), null, null, null, null, null));

        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> resultado = ticketService.getAllTicket();

        assertThat(resultado).isEqualTo(tickets);
    }

    @Test
    void listTicketsbyID_returnCorrectTicket(){
        Long idTicket = 1L;
        Ticket ticket = new Ticket(idTicket, new Date(), null, null, null, null, null);

        when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));

        Optional<Ticket> resultado = ticketService.getById(idTicket);

        assertThat(resultado).contains(ticket);
    }


    @Test
    void listTicketByIDusuario_returnTickets(){
        Long idUsuario = 1L;
        List<Ticket> tickets = Arrays.asList(new Ticket(1L, new Date(), null, null, idUsuario, null, null),
        new Ticket(2L, new Date(), null, null, idUsuario, null, null));

        when(ticketRepository.findByIdUsuario(idUsuario)).thenReturn(tickets);

        List<Ticket> resultado = ticketService.findbyidUsuario(idUsuario);

        assertThat(resultado).isEqualTo(tickets);
    }

    @Test
    void createTicketTest_returnsCreatedTicket(){
        Long idUsuario = 1L;
        Long idEstado = 1L;
        Ticket ticket = new Ticket(1L, new Date(), null, idEstado, idUsuario, null, null);

        doReturn(true).when(ticketService).validarUsuario(idUsuario);
        doReturn(true).when(ticketService).validarEstado(idEstado);

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket resultado = ticketService.createTicket(ticket);

        assertThat(resultado).isEqualTo(resultado);

    }

    @Test 
    void validateUserTest_retunrsTrue(){
        Long idUsuario = 1L;
        Ticket ticket = new Ticket();
        ticket.setIdUsuario(idUsuario);

        Map<String, Object> response = new HashMap<>();
        response.put("existe", true);

        when(restTemplate.getForObject(anyString(),eq(Map.class),eq(1L))).thenReturn(response);

        Boolean resultado = ticketService.validarUsuario(idUsuario);
        assertTrue(resultado);
    }

    @Test
    void validarEstadoTest_returnsActivo() {
    Long idEstado = 1L;
    Long idTicket = 1L;
     Ticket ticket = new Ticket();
     ticket.setIdTicket(idTicket);
     ticket.setIdEstado(idEstado);

    when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));

    Map<String, Object> response = new HashMap<>();
    response.put("nombre", "Activo"); 

    when(restTemplate.getForObject(anyString(), eq(Map.class), eq(idEstado))).thenReturn(response);

    String resultado = ticketService.obtenerEstadoDelTicket(1L);

    assertEquals("Activo", resultado);
}

@Test
void validarEstado_returnsTrue() {
    Long idEstado = 1L;

    Map<String, Object> response = new HashMap<>();
    response.put("nombre", "Activo"); 

    when(restTemplate.getForObject(anyString(), eq(Map.class), eq(idEstado))).thenReturn(response);

    boolean resultado = ticketService.validarEstado(idEstado);

    assertTrue(resultado);
}






}
