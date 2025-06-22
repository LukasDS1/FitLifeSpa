package com.example.soporteservice.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.HistorialRepository;

@ExtendWith(MockitoExtension.class)
public class HistorialServiceTest {

    @Mock
    private HistorialRepository historialRepository;

    @InjectMocks
    private HistorialService historialService;

    @Test
    void getAllHistoriales_returnsAll(){
        List<Historial> historiales = Arrays.asList(new Historial(1L, "historial1", "denuncia", new Date(), null),
        new Historial(2L, "historial1", "denuncia", new Date(), null));

        when(historialRepository.findAll()).thenReturn(historiales);

        List<Historial> resultado = historialService.getHistorial();

        assertThat(resultado).isEqualTo(historiales);
    }

    @Test
    void getHistorialesById_returnsID(){
        Long idHistorial = 1L;
        Historial historial = new Historial(idHistorial, "historial1", "denuncia", new Date(), null);


        when(historialRepository.findById(idHistorial)).thenReturn(Optional.of(historial));

        Historial resultado = historialService.getHistorialById(idHistorial);

        assertThat(resultado).isEqualTo(historial);     
    }


    @Test
    void getHistorialesByTicketId_returnsID(){
      
        Long idTicket = 2L;

        Ticket ticket = new Ticket(idTicket, new Date(), null, null, null, null, null);


        List<Historial> historiales = Arrays.asList(new Historial(1L, "historial1", "denuncia", new Date(), ticket),
        new Historial(2L, "historial1", "denuncia", new Date(), ticket));

        when(historialRepository.findByTicketIdTicket(idTicket)).thenReturn(historiales);

        List<Historial> result = historialService.getHistorialesByTicketId(idTicket);

        assertThat(result).isEqualTo(historiales);


    }



    

}
