package com.example.reserva_service.service;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.example.reserva_service.model.EstadoReserva;
import com.example.reserva_service.repository.estadoReservaRepository;

@ExtendWith(MockitoExtension.class)
public class estadoReservaServiceTest {
    @Mock
    private estadoReservaRepository repository;

    @InjectMocks
    @Spy
    private estadoReservaService service;

    @Mock
    private RestTemplate client;

    @Test
    void listarTodo_returnsListFromRepository(){
        List<EstadoReserva> mockList = Arrays.asList(new EstadoReserva());

        when(repository.findAll()).thenReturn(mockList);

        List<EstadoReserva> results = service.listarTodo();

        assertThat(results).isEqualTo(mockList);
    }

    @Test
    void listarPorId_returnsListFromRepository(){
        Optional<EstadoReserva> mockObj =Optional.ofNullable(new EstadoReserva(1L,new Date(), "Activo", 1L, null));

        Long testId = 1L;
        when(repository.findById(testId)).thenReturn(mockObj);

        EstadoReserva results = service.listarPorId(testId);

        assertThat(results).isSameAs(mockObj.get());
    }

    @Test
    void agregarEstadoReserva_returnsEstadoReserva(){
        EstadoReserva mockObjt = new EstadoReserva();

        doReturn(true).when(service).validarEstado(mockObjt);

        when(repository.save(mockObjt)).thenReturn(mockObjt);

        EstadoReserva result = service.agregarEstadoReserva(mockObjt);

        assertThat(result).isSameAs(mockObjt);
    }

    @Test
    void validarServicio_returnsTrue(){
        EstadoReserva mockObjt = new EstadoReserva();
        mockObjt.setIdEstado(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("existe", true);

        when(client.getForObject(anyString(), eq(Map.class), eq(1L))).thenReturn(response);

        Boolean result = service.validarEstado(mockObjt);

        assertTrue(result);
    }


    @Test
    void eliminarEstadoReserva_executesProperly(){
        Long testID = 1L;
        when(repository.existsById(testID)).thenReturn(true);

        service.eliminarEstadoReserva(testID);

        verify(repository, times(1)).deleteById(testID);
    }

    
}
