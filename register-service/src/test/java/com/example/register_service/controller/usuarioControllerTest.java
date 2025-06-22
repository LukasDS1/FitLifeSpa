package com.example.register_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;



@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)
public class usuarioControllerTest {

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    //Aqui probarsemos si el metodo alojado en services trae una lista con todos los usuarios
    @Test
    void getAllUsers_returnsOKJson(){
        List<Usuario> usuarios = Arrays.asList(new Usuario(1L, "test@gmail.com", "test1", "test1", "test1", "test1", "test1", "test1", null),
        new Usuario(1L, "test2@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null));

        when(usuarioService.findAll()).thenReturn(usuarios);

        try {
           mockMvc.perform(get("/api-v1/register/getall")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Aqui probaremos si el metodo para listar usuarios por id trae al usuario correctamente
    @Test
    void getUserbyID_returnsUser(){
       Long idUsuario = 1L;

       Usuario usuario =  new Usuario(idUsuario, "test2@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null);

       when(usuarioService.findByID(idUsuario)).thenReturn(usuario);

       try {
        mockMvc.perform(get("/api-v1/register/exists/{id}",idUsuario)).andExpect(status().isOk()).andExpect(jsonPath("$.idUsuario").value(1L));
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

    //Aqui probaremos si el metodo para obtener usuarios por email trae al usuario correctamente
    @Test
    void getUserByEmail_returnsUser(){

        Usuario usuario =  new Usuario(1L, "test2@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null);

        when(usuarioService.getByMail("test2@gmail.com")).thenReturn(usuario);

        try {
            mockMvc.perform(post("/api-v1/register/exists").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"test2@gmail.com\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.email").
            value("test2@gmail.com")); //usamos el mediaType y contentType para que reconozca que el email viene por un json
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Aqui probaremos si el metodo para borrar usuarios devuelve un OK con el texto que leyenda de la siguiente forma "el usuario ha sido borrado con exito!"
    @Test
    void deleteuserById_returnsok(){
        Long idUsuario = 1L;
        doNothing().when(usuarioService).deleteByid(idUsuario);
        try {
            mockMvc.perform(delete("/api-v1/register/delete/{idUsuario}",idUsuario)).andExpect(status().isOk()).andExpect(content().string("El usuario ha sido borrado con exito!"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Aqui probaremos si el metood para crear usuarios devuelve un codigo 201 CREATED 
    @Test
    void createUserTest_returnsCreatedUseriscreated() throws Exception{
        Usuario usuario =  new Usuario(1L, "test2@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null);
        
        when(usuarioService.getByMail(usuario.getEmail())).thenReturn(null);

        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(usuario);

        try {
            mockMvc.perform(post("/api-v1/register/crearUsuario").contentType(MediaType.APPLICATION_JSON).content(Json)).andExpect(status().isCreated()).
            andExpect(content().string("Usuario creado correctamente."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Aqui probaremos si el metodo para actualizar usuarios actualiza de forma correcta el email de el usuario y de ser asi lanza un codigo OK
    @Test
    void updateusuarioByID_returnsUpdatedUsers(){
        Long idUsuario = 10L;
        
        Usuario usuario =  new Usuario(idUsuario, "test@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null);

        Usuario usuario2 =  new Usuario(idUsuario, "test2@gmail.com", "test2", "test2", "test2", "test2", "test2", "test2", null);

        when(usuarioService.UpdateUserById(any(Usuario.class))).thenReturn(usuario2);

        try {
            mockMvc.perform(patch("/api-v1/register/actualizar/{idUsuario}", idUsuario).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
            .writeValueAsString(usuario))).andExpect(status().isOk()).andExpect(jsonPath("$.email").value("test2@gmail.com"));       
         } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
