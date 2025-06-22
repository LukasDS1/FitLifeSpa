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

import com.example.privileges_service.model.Rol;
import com.example.privileges_service.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {
    
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;
    
    
    
    
    @Test
    void validarEstado_returnsEstado_whenExists() {
        Rol rol = new Rol();
        when(rolRepository.existsById(1L)).thenReturn(true);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Rol result = rolService.validarRol(1L);

        assertThat(result).isEqualTo(rol);
        verify(rolRepository).existsById(1L);
        verify(rolRepository).findById(1L);
    }

    @Test
    void agregarEstado_returnsSavedEstado() {
        Rol rol = new Rol();
        when(rolRepository.save(rol)).thenReturn(rol);

        Rol result = rolService.AgregarRol(rol);

        assertThat(result).isEqualTo(rol);
        verify(rolRepository).save(rol);
    }
}
