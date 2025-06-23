package com.example.gymservices_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gymservices_service.model.Servicio;
import com.example.gymservices_service.repository.ServicioRepository;

@ExtendWith(MockitoExtension.class)
public class ServicesServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new Servicio(1L, "Yoga", "Clases de yoga para principantes");
    }

    @Test
    void allServices_returnsListOfServicios() {
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio));

        List<Servicio> result = servicioService.allServices();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(servicioRepository).findAll();
    }

    @Test
    void addService_savesAndReturnsServicio() {
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        Servicio result = servicioService.addService(servicio);

        assertNotNull(result);
        assertEquals("Yoga", result.getNombre());
        verify(servicioRepository).save(servicio);
    }

    @Test
    void serviceById_returnsServicioWhenExists() {
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        Servicio result = servicioService.serviceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdServicio());
        verify(servicioRepository).findById(1L);
    }

    
    @Test
    void deleteService_sucessful() {
        when(servicioRepository.existsById(1L)).thenReturn(true);

        Boolean result = servicioService.deleteService(1L);

        assertTrue(result);
        verify(servicioRepository).deleteById(1L);
    }
    
    @Test
    void updateService_returnsUpdatedServicio() {
        Servicio newServicio = new Servicio(null, "Pilates", "Clases de pilates");

        when(servicioRepository.existsById(1L)).thenReturn(true);
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(servicioRepository.save(any())).thenReturn(servicio);

        Servicio result = servicioService.updateService(1L, newServicio);

        assertNotNull(result);
        assertEquals("Pilates", result.getNombre());
        verify(servicioRepository).save(any());
    }
}
