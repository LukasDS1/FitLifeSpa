package com.example.inscripcion_service.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


import com.example.inscripcion_service.model.Estado;
import com.example.inscripcion_service.repository.EstadoRepository;

@ExtendWith(MockitoExtension.class)
public class EstadoServiceTest {
    @Mock
    private EstadoRepository estadoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EstadoService estadoService;

    
    @Test
    void validarClase_returnsTrueIfExist() {
        Estado estado = new Estado();
        estado.setIdEstado(99L);

        Map<String, Object> estadoMock = Map.of("id", 99);
        when(restTemplate.getForObject(anyString(), eq(Map.class), eq(99L)))
            .thenReturn(estadoMock);

        boolean result = estadoService.validarEstado(estado);

        assertThat(result).isTrue();
    }
}
