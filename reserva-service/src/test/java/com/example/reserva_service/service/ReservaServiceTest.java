package com.example.reserva_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    @Mock
    private ReservaRepository repository;
    
    @Spy
    @InjectMocks
    private ReservaService service;

    @Mock
    private RestTemplate client;

    @Test
    void listarReservas_returnsListFromRepository(){
        List<Reserva> mockList = Arrays.asList(new Reserva(1L, new Date(), new Date(), "Entrenamiento saludable", 1L, 1L,2L, null));

        when(repository.findAll()).thenReturn(mockList);

        List<Reserva> results = service.listarReservas();

        assertThat(results).isEqualTo(mockList);
    }

    @Test
    void listarReservas_returnsEmptyFromRepository(){
        List<Reserva> mockList = new ArrayList<>();

        when(repository.findAll()).thenReturn(mockList);

        List<Reserva> results = service.listarReservas();

        assertThat(results).isEmpty();
    }


    @Test
    void agregarReserva_returnsReservaFromRepository(){
        Reserva mockObjt = new Reserva(1L, new Date(), new Date(), "Entrenamiento saludable", 1L, 1L,2L, null);

        doReturn(true).when(service).validarServicio(mockObjt);
        doReturn(true).when(service).validarUsuario(mockObjt);

        when(repository.save(mockObjt)).thenReturn(mockObjt);

        Reserva result = service.agregarReserva(mockObjt);

        assertThat(result).isSameAs(mockObjt);
    }


    @Test
    void agregarReserva_returnsNullFromServiceWhenValidarServicioIsFalse(){
        Reserva mockObjt = new Reserva();

        doReturn(false).when(service).validarServicio(mockObjt);

        Reserva result = service.agregarReserva(mockObjt);

        assertNull(result);

        verify(repository, never()).save(any());
    }

    @Test
    void agregarReserva_returnsNullFromServiceWhenValidarUsuarioIsFalse(){
        Reserva mockObjt = new Reserva();

        doReturn(true).when(service).validarServicio(mockObjt);
        doReturn(false).when(service).validarUsuario(mockObjt);

        Reserva result = service.agregarReserva(mockObjt);

        assertNull(result);

        verify(repository, never()).save(any());
    }



    @Test
    void listarReservasPorUser_returnsListFromRepository(){
        List<Reserva> mockList = Arrays.asList(new Reserva(1L, new Date(), new Date(), "Entrenamiento saludable", 1L, 1L,2L, null));

        when(repository.findByIdUsuario(1L)).thenReturn(mockList);

        List<Reserva> results = service.listarPorIdUser(1L);

        assertThat(results).isEqualTo(mockList);
    }


    @Test
    void listarReservasPorUser_returnsEmptyFromRepository(){
        List<Reserva> mockList = new ArrayList<>();

        when(repository.findByIdUsuario(80L)).thenReturn(mockList);

        List<Reserva> results = service.listarPorIdUser(80L);

        assertThat(results).isEmpty();
    }

    @Test
    void buscarPorId_returnObjetReservaFromRepository(){
        Optional<Reserva> mockObj = Optional.ofNullable(new Reserva(1L, new Date(), new Date(), "Entrenamiento saludable", 1L, 1L,2L, null));

        when(repository.findById(1L)).thenReturn(mockObj);

        Reserva result = service.buscarPorId(1L);

        assertThat(result).isSameAs(mockObj.get());
        
    }

    @Test
    void buscarPorId_returnNullFromRepository(){
        Long id = 999L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Reserva result = service.buscarPorId(id);

        assertNull(result);
        
    }

    @Test
    void borrarReserva_executesCorrectly(){
        Long idReserva = 1L;

        service.borrarReserva(idReserva);

        // Verificamos que deleteById se llamó una vez con el ID correcto
        verify(repository, times(1)).deleteById(idReserva);

    }

    @Test
    void validarUsuario_ReturnsTrueIfFound() {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("existe", true);

        when(client.getForObject(anyString(), eq(Map.class), eq(1L))).thenReturn(response);

        Boolean resultado = service.validarUsuario(reserva);
        assertTrue(resultado);
    }

    @Test
    void validarUsuario_returnsFalseIfGeneralError() {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(5L);

        when(client.getForObject(anyString(), eq(Map.class), eq(5L)))
            .thenThrow(new RuntimeException("Error desconocido"));

        Boolean results = service.validarUsuario(reserva);
        assertFalse(results);
    }

    @Test
    void testValidarUsuario_returnsFalseIfNotExist() {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(2L);

        when(client.getForObject(anyString(), eq(Map.class), eq(2L))).thenReturn(new HashMap<>());

        Boolean results = service.validarUsuario(reserva);
        assertFalse(results);
    }

    @Test
    void testValidarUsuario_returnsFalseIfHttpStatusCode404FromClient() {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(4L);
        
        when(client.getForObject(anyString(), eq(Map.class), eq(4L)))
            .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        Boolean results = service.validarUsuario(reserva);
        assertFalse(results);
    }


    @Test
    void validarServicio_returnsTrueIfFound(){
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("existe", true);

        when(client.getForObject(anyString(), eq(Map.class), eq(1L))).thenReturn(response);

        Boolean resultado = service.validarUsuario(reserva);
        assertTrue(resultado);
    }

}
