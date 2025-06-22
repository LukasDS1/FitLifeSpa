package com.example.usermanagment.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.usermanagment.dto.RolDTO;
import com.example.usermanagment.dto.UsuarioDTO;

@ExtendWith(MockitoExtension.class)
public class UserManagmentServiceTest {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks 
    private UsuarioService usuarioService;

    @Test
    void listReturnallusers(){
        RolDTO rolDTO = new RolDTO(1L, "Cliente");
        UsuarioDTO usuario1 = new UsuarioDTO(10L, "test@gmail.com", "pass1", "test1", "test1", "test1", "genero1", "12345",rolDTO);
        UsuarioDTO usuario2 = new UsuarioDTO(11L, "test2@gmail.com", "pass2", "test2", "test2", "test2", "genero2", "54321",rolDTO);

        UsuarioDTO[] usuarios = new UsuarioDTO[]{usuario1,usuario2};

        when(restTemplate.getForObject("http://localhost:8082/api-v1/register/getall", UsuarioDTO[].class)).thenReturn(usuarios);

        List<UsuarioDTO> resultado = usuarioService.listAllUsers();
        
        assertThat(resultado).isEqualTo(Arrays.asList(usuarios));
    }

    @Test
    void fidbyidReturUser(){
        Long idUsuario = 10L;
        RolDTO rolDTO = new RolDTO(1L, "Cliente");
        UsuarioDTO usuario1 = new UsuarioDTO(10L, "test@gmail.com", "pass1", "test1", "test1", "test1", "genero1", "12345",rolDTO);
        
        when(restTemplate.getForObject("http://localhost:8082/api-v1/register/exists/{idUsuario}", UsuarioDTO.class, idUsuario)).thenReturn(usuario1);

        UsuarioDTO resultado = usuarioService.findByID(idUsuario);

        assertThat(resultado).isEqualTo(usuario1);
        
    }

    @Test
    void deletebyiddeleteUser(){
        Long idUsuario = 3L;

        doNothing().when(restTemplate).delete("http://localhost:8082/api-v1/register/delete/{idUsuario}", idUsuario);

        usuarioService.deleteUser(idUsuario);

        verify(restTemplate, times(1)).delete("http://localhost:8082/api-v1/register/delete/{idUsuario}", idUsuario);
      

    }

}
