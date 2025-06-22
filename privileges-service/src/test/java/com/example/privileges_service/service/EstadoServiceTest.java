package com.example.privileges_service.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.privileges_service.model.Estado;
import com.example.privileges_service.repository.EstadoRepository;

@ExtendWith(MockitoExtension.class)
public class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepo;

    @InjectMocks
    private EstadoService estadoService;


    @Test
    void validarEstado_returnsEstado_whenExists() {
        Estado estado = new Estado();
        when(estadoRepo.existsById(1L)).thenReturn(true);
        when(estadoRepo.findById(1L)).thenReturn(Optional.of(estado));

        Estado result = estadoService.validarEstado(1L);

        assertThat(result).isEqualTo(estado);
        verify(estadoRepo).existsById(1L);
        verify(estadoRepo).findById(1L);
    }

    @Test
    void agregarEstado_returnsSavedEstado() {
        Estado estado = new Estado();
        when(estadoRepo.save(estado)).thenReturn(estado);

        Estado result = estadoService.agregarEstado(estado);

        assertThat(result).isEqualTo(estado);
        verify(estadoRepo).save(estado);
    }

}
