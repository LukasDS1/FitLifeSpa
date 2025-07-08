package com.example.privileges_service.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.privileges_service.model.Estado;
import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.model.Rol;
import com.example.privileges_service.repository.EstadoRepository;
import com.example.privileges_service.repository.ModuloRepository;
import com.example.privileges_service.repository.PrivilegesRepository;
import com.example.privileges_service.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class PrivilegesServiceTest {

    @Mock
    private PrivilegesRepository privRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @Mock
    private ModuloRepository moduloRepository;

    @InjectMocks
    private PrivilegesService privilegesService;

    @Test
    void allPrivileges_returnsAllFromRepository() {
        List<Privileges> list = List.of(new Privileges(), new Privileges());

        when(privRepository.findAll()).thenReturn(list);

        List<Privileges> result = privilegesService.allPrivileges();
        
        assertThat(result).isEqualTo(list);
        verify(privRepository).findAll();
    }

    @Test
    void findPrivById_returnsPrivilege_whenExists() {
        Privileges privilege = new Privileges();

        when(privRepository.findById(10L)).thenReturn(Optional.of(privilege));

        Privileges result = privilegesService.findPrivById(10L);

        assertThat(result).isEqualTo(privilege);
    }

    @Test
    void addPrivileges_returnsSavedPrivilege() {
    Privileges privilege = new Privileges();

        when(privRepository.save(privilege)).thenReturn(privilege);

        Privileges result = privilegesService.addPrivileges(privilege);

        assertThat(result).isEqualTo(privilege);
        verify(privRepository).save(privilege);
    }

    @Test
    void deletePrivileges_returnsTrue_whenExists() {
        when(privRepository.existsById(1L)).thenReturn(true);

        boolean result = privilegesService.deletePrivileges(1L);

        assertThat(result).isTrue();
        verify(privRepository).deleteById(1L);
    }

    @Test
    void findPrivilegesByRol_returnsList_whenRolExists() {
        Rol rol = new Rol();
        List<Privileges> list = List.of(new Privileges());

        when(rolRepository.existsById(2L)).thenReturn(true);
        when(rolRepository.findById(2L)).thenReturn(Optional.of(rol));
        when(privRepository.findByRol(rol)).thenReturn(list);

        List<Privileges> result = privilegesService.findPrivilegesByRol(2L);

        assertThat(result).isEqualTo(list);
    }

    @Test
    void findPrivilegeByEstado_returnsList_whenEstadoExists() {
        Estado estado = new Estado();
        List<Privileges> list = List.of(new Privileges());

        when(estadoRepository.existsById(3L)).thenReturn(true);
        when(estadoRepository.findById(3L)).thenReturn(Optional.of(estado));
        when(privRepository.findByEstado(estado)).thenReturn(list);

        List<Privileges> result = privilegesService.findPrivilegeByEstado(3L);

        assertThat(result).isEqualTo(list);
    }

    @Test
    void findPrivilegesByModulo_returnsList_whenModuloExists() {
        Modulo modulo = new Modulo();
        List<Privileges> list = List.of(new Privileges());

        when(moduloRepository.existsById(4L)).thenReturn(true);
        when(moduloRepository.findById(4L)).thenReturn(Optional.of(modulo));
        when(privRepository.findByModulo(modulo)).thenReturn(list);

        List<Privileges> result = privilegesService.findPrivilegesByModulo(4L);

        assertThat(result).isEqualTo(list);
    }

}
