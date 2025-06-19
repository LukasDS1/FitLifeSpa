package com.example.inscripcion_service.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.inscripcion_service.model.Inscripcion;
import com.example.inscripcion_service.repository.InscripcionRepository;

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Test
    void listarInscripcion_returnsListFromRepo() {
        List<Inscripcion> lista = List.of(new Inscripcion(), new Inscripcion());
        when(inscripRepo.findAll()).thenReturn(lista);

        List<Inscripcion> result = inscripcionService.listarInscripcion();

        assertThat(result).isEqualTo(lista);
        verify(inscripRepo).findAll();
    }

    @Test
    void buscarporId_returnsInscripcion_whenExists() {
        Inscripcion insc = new Inscripcion();
        when(inscripRepo.findById(1L)).thenReturn(Optional.of(insc));

        Inscripcion result = inscripcionService.buscarporId(1L);

        assertThat(result).isEqualTo(insc);
        verify(inscripRepo).findById(1L);
    }

    @Test
    void validacion_returnsTrue_whenExists() {
        when(inscripRepo.existsById(5L)).thenReturn(true);

        boolean result = inscripcionService.validacion(5L);

        assertThat(result).isTrue();
        verify(inscripRepo).existsById(5L);
    }

    @Test
    void eliminarInscripcion_callsDelete_whenValid() {
        when(inscripRepo.existsById(10L)).thenReturn(true);

        inscripcionService.eliminarInscripcion(10L);

        verify(inscripRepo).deleteById(10L);
    }

    @Test
    void agragarInscripcion_returnsSavedInscripcion_whenUserExists() {
        Inscripcion insc = new Inscripcion();
        insc.setIdUsuario(99L);

        Map<String, Object> usuarioMock = Map.of("id", 99);
        when(restTemplate.getForObject(anyString(), eq(Map.class), eq(99L)))
            .thenReturn(usuarioMock);

        when(inscripRepo.save(insc)).thenReturn(insc);

        Inscripcion result = inscripcionService.agragarInscripcion(insc);

        assertThat(result).isEqualTo(insc);
        verify(inscripRepo).save(insc);
    }

    @Test
    void listarIncripcionesPorEstado_returnsList() {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscripRepo.findByIdEstado(2L)).thenReturn(lista);

        List<Inscripcion> result = inscripcionService.listarIncripcionesPorEstado(2L);

        assertThat(result).isEqualTo(lista);
    }

    @Test
    void listarIncripcionesPorClase_returnsList() {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscripRepo.FindByIdClase(3L)).thenReturn(lista);

        List<Inscripcion> result = inscripcionService.listarIncripcionesPorClase(3L);

        assertThat(result).isEqualTo(lista);
    }

    @Test
    void listarPorUsuario_returnsList() {
        List<Inscripcion> lista = List.of(new Inscripcion());
        when(inscripRepo.findByIdUsuario(4L)).thenReturn(lista);

        List<Inscripcion> result = inscripcionService.listarPorUsuario(4L);

        assertThat(result).isEqualTo(lista);
    }


}
