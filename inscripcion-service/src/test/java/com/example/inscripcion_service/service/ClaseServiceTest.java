package com.example.inscripcion_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.repository.ClaseRepository;

@ExtendWith(MockitoExtension.class)
public class ClaseServiceTest {
    @Mock
    private ClaseRepository claseRepository;

    @InjectMocks
    private ClaseService claseService;

    @Mock
    private RestTemplate restTemplate;


    @Test
    void validarClase_returnsTrueIfExist() {
        Clase clase = new Clase();
        clase.setIdClase(99L);

        Map<String, Object> claseMock = Map.of("id", 99);
        when(restTemplate.getForObject(anyString(), eq(Map.class), eq(99L)))
            .thenReturn(claseMock);

        boolean result = claseService.validarClase(clase);

        assertThat(result).isTrue();
    }
}
