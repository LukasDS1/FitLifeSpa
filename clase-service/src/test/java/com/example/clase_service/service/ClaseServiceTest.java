package com.example.clase_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.clase_service.model.Clase;
import com.example.clase_service.repository.ClaseRepository;

@ExtendWith(MockitoExtension.class)
public class ClaseServiceTest {

    @Mock
    private ClaseRepository claseRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClaseService claseService;

    private Clase sampleClase;

    @BeforeEach
    void setUp() {
        sampleClase = new Clase(1L, "Yoga", new Date(), "Clase de yoga", 5L);
    }

    @Test
    void getAllClases_returnsList() {
        List<Clase> lista = List.of(sampleClase);
        when(claseRepository.findAll()).thenReturn(lista);

        List<Clase> result = claseService.getAllClases();

        assertEquals(1, result.size());
        verify(claseRepository).findAll();
    }

        @Test
    void saveClase_whenServicioValid_thenSave() {
        when(restTemplate.getForObject(anyString(), eq(Map.class), anyLong()))
            .thenReturn(Map.of("idServicio", 5L));
        when(claseRepository.save(sampleClase)).thenReturn(sampleClase);

        Clase result = claseService.saveClase(sampleClase);

        assertNotNull(result);
        verify(claseRepository).save(sampleClase);
    }

    @Test
    void getClaseById_returnsClaseWhenfound() {
        when(claseRepository.findById(1L)).thenReturn(Optional.of(sampleClase));

        Optional<Clase> result = claseService.getClaseById(1L);

        assertTrue(result.isPresent());
        assertEquals("Yoga", result.get().getNombre());
    }

    @Test
    void deleteClase_successful() {
        when(claseRepository.findById(1L)).thenReturn(Optional.of(sampleClase));

        claseService.deleteClase(1L);

        verify(claseRepository).deleteById(1L);
    }

    @Test
    void updateClase_successful() {
        when(claseRepository.findById(1L)).thenReturn(Optional.of(sampleClase));
        when(claseRepository.save(any(Clase.class))).thenReturn(sampleClase);

        sampleClase.setDescripcion("Actualizada");
        Clase result = claseService.updateClase(1L, sampleClase);

        assertEquals("Actualizada", result.getDescripcion());
        verify(claseRepository).save(any(Clase.class));
    }

    @Test
    void obtenerServicioDeClase_successful() {
        when(claseRepository.findById(1L)).thenReturn(Optional.of(sampleClase));
        when(restTemplate.getForObject(anyString(), eq(Map.class), anyLong()))
            .thenReturn(Map.of("idServicio", 5L, "nombre", "Fitness"));

        Map<String, Object> result = claseService.obtenerServicioDeClase(1L);

        assertEquals("Fitness", result.get("nombre"));
    }
}
