package com.example.soporteservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.repository.MotivoRepository;

@ExtendWith(MockitoExtension.class)
public class MotivoServiceTest {

    @Mock
    private MotivoRepository motivoRepository;

    @InjectMocks
    private MotivoService motivoService;

    @Test
    void findAllMotivos_returnAList(){
        List<Motivo> motivos = Arrays.asList(new Motivo(1L, "motivo1", null),
        new Motivo(2L, "motivo2", null));

        when(motivoRepository.findAll()).thenReturn(motivos);

        List<Motivo> resultado = motivoService.getAllMotivo();

        assertThat(resultado).isEqualTo(motivos);
    }

    @Test
    void getMotivoByID_returnsMotivo(){
        Long idMotivo = 1L;
        Motivo motivo =  new Motivo(idMotivo, "motivo1", null);

        when(motivoRepository.findById(idMotivo)).thenReturn(Optional.of(motivo));

        Optional<Motivo> resultado = motivoService.getMotivo(idMotivo);

        assertThat(resultado).contains(motivo);
    }

    @Test
    void createMotivoTest_returnsCreatedMotivo(){
        Motivo motivo =  new Motivo(1L, "motivo1", null);

        when(motivoRepository.save(motivo)).thenReturn(motivo);

        Motivo result = motivoService.createMotivo(motivo);

        assertThat(result).isEqualTo(motivo);
    }

    

}
