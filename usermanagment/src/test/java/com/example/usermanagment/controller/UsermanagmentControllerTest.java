package com.example.usermanagment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.usermanagment.dto.RolDTO;
import com.example.usermanagment.dto.UsuarioDTO;
import com.example.usermanagment.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)

public class UsermanagmentControllerTest {

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAllUsers_returnsOKJson(){
        RolDTO rolDTO = new RolDTO(1L, "Cliente");
        List<UsuarioDTO> usuarios = Arrays.asList(new UsuarioDTO(10L, "test@gmail.com", "pass1", "test1", "test1", "test1", "genero1", "12345",rolDTO));

        when(usuarioService.listAllUsers()).thenReturn(usuarios);
       try {
        mockMvc.perform(get("/api-v1/managment/all")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }


    @Test 
    void getUserbyID_returnsOKJSON(){
        Long idUsuario = 10L;
        RolDTO rolDTO = new RolDTO(1L, "Cliente");
        UsuarioDTO usuario1 = new UsuarioDTO(10L, "test@gmail.com", "pass1", "test1", "test1", "test1", "genero1", "12345",rolDTO);

        when(usuarioService.findByID(idUsuario)).thenReturn(usuario1);

        try {
            mockMvc.perform(get("api-v1/managment/listid/{idUsuario}",idUsuario)).andExpect(status().isOk()).andExpect(jsonPath("$.idUsuario").value(10L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 


    @Test
    void deleteUserByID_returnsOKJSON(){
        Long idUsuario = 10L;
        
        doNothing().when(usuarioService).deleteUser(idUsuario);

        try {
            mockMvc.perform(delete("/api-v1/managment/delete/{idUsuario}",idUsuario)).andExpect(status().isOk()).andExpect(content().string("El usuario ha sido borrado con exito"));
        } catch (Exception e) {
           e.printStackTrace();
        }

    }


    @Test
    void updateUserById_returnsOKJSON(){
        Long idUsuario = 10L;
        RolDTO rolDTO = new RolDTO(1L, "Cliente");
        UsuarioDTO usuarioAntiguo = new UsuarioDTO(10L, "test1@gmail.com", "pass1", "test2", "test1", "test1", "genero1", "12345",rolDTO);
        UsuarioDTO usuarioNuevo = new UsuarioDTO(10L, "test2@gmail.com", "pass1", "test1", "test1", "test1", "genero1", "12345",rolDTO);

        when(usuarioService.updateUser(eq(idUsuario), any(UsuarioDTO.class))).thenReturn(usuarioNuevo);

        try {
            mockMvc.perform(patch("/api-v1/managment/update/{id}", idUsuario)
            .contentType("application/json").content(new ObjectMapper().writeValueAsString(usuarioAntiguo))).andExpect(status().isOk()).andExpect(jsonPath("$.email").value("test2@gmail.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
