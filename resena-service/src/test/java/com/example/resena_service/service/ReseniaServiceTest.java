package com.example.resena_service.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.resena_service.model.Resenia;
import com.example.resena_service.repository.ReseniaRepository;

@ExtendWith(MockitoExtension.class)
public class ReseniaServiceTest {

    
    @Mock
    private ReseniaRepository reseniaRepository;

    @Mock
    private RestTemplate cliente;

    @InjectMocks
    private ReseniaService reseniaService;

    @Test
    void listar_returnsListFromRepository() {
        List<Resenia> mockList = List.of(new Resenia(), new Resenia());

        when(reseniaRepository.findAll()).thenReturn(mockList);

        List<Resenia> result = reseniaService.listar();

        assertThat(result).isEqualTo(mockList);
        verify(reseniaRepository).findAll();
    }


    @Test
    void buscarId_returnsResenia_whenExists() {
        Resenia resenia = new Resenia();
        when(reseniaRepository.findById(1L)).thenReturn(Optional.of(resenia));

        Resenia result = reseniaService.buscarId(1L);

        assertThat(result).isEqualTo(resenia);
        verify(reseniaRepository).findById(1L);
    }


    @Test
    void buscarPorIdServicio_returnsListFromRepository() {
        List<Resenia> resenias = List.of(new Resenia());
        when(reseniaRepository.findByIdServicio(10L)).thenReturn(resenias);

        List<Resenia> result = reseniaService.buscarPorIdServicio(10L);

        assertThat(result).isEqualTo(resenias);
        verify(reseniaRepository).findByIdServicio(10L);
    }

    @Test
    void validarServicio_returnsTrue_whenServicioExiste() {
        Resenia resenia = new Resenia();
        resenia.setIdServicio(99L);
        Map<String, Object> response = Map.of("id", 99);

        when(cliente.getForObject(anyString(), eq(Map.class), eq(99L))).thenReturn(response);

        boolean result = reseniaService.validarServicio(resenia);

        assertTrue(result);
    }

    @Test
    void validarUsuario_returnsTrue_whenUsuarioExiste() {
        Resenia resenia = new Resenia();
        resenia.setIdUsuario(88L);
        Map<String, Object> response = Map.of("id", 88);

        when(cliente.getForObject(anyString(), eq(Map.class), eq(88L))).thenReturn(response);

        boolean result = reseniaService.validarUsuario(resenia);

        assertTrue(result);
    }


    @Test
    void agregarResenia_savesResenia_whenValidacionesSonTrue() {
        Resenia resenia = new Resenia();
        resenia.setIdUsuario(88L);
        resenia.setIdServicio(99L);

        when(cliente.getForObject(contains("service"), eq(Map.class), eq(99L)))
            .thenReturn(Map.of("id", 99));
        when(cliente.getForObject(contains("register"), eq(Map.class), eq(88L)))
            .thenReturn(Map.of("id", 88));
        when(reseniaRepository.save(resenia)).thenReturn(resenia);

        Resenia result = reseniaService.agregarResenia(resenia);

        assertThat(result).isEqualTo(resenia);
        verify(reseniaRepository).save(resenia);
    }

    @Test
    void eliminar_deletesResenia_whenExists() {
        when(reseniaRepository.existsById(5L)).thenReturn(true);

        reseniaService.Eliminar(5L);

        verify(reseniaRepository).deleteById(5L);
    }


}
