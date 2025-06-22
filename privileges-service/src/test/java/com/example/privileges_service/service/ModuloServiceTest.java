package com.example.privileges_service.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.repository.ModuloRepository;

@ExtendWith(MockitoExtension.class)
public class ModuloServiceTest {
    
    @Mock
    private ModuloRepository moduloRepository;

    @InjectMocks
    private ModuloService moduloService;
    
    @Test
    void validarEstado_returnsEstado_whenExists() {
        Modulo modulo = new Modulo();
        when(moduloRepository.existsById(1L)).thenReturn(true);
        when(moduloRepository.findById(1L)).thenReturn(Optional.of(modulo));

        Modulo result = moduloService.validarModulo(1L);

        assertThat(result).isEqualTo(modulo);
        verify(moduloRepository).existsById(1L);
        verify(moduloRepository).findById(1L);
    }

    @Test
    void agregarEstado_returnsSavedEstado() {
        Modulo modulo = new Modulo();
        when(moduloRepository.save(modulo)).thenReturn(modulo);

        Modulo result = moduloService.agregarModulo(modulo);

        assertThat(result).isEqualTo(modulo);
        verify(moduloRepository).save(modulo);
    }

}
