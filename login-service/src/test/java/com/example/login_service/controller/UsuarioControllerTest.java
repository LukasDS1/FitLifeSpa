package com.example.login_service.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.login_service.service.UsuarioService;


@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void validateUsuario_Returns202WhenCredentialsAreValid() throws Exception {
        
        when(usuarioService.validateUser(anyString(), anyString())).thenReturn(true);

        String requestBody = """
        {
        "email": "usuario@gmail.com",
        "password": "12345"
        }
        """;

        mockMvc.perform(post("/api-v1/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isAccepted())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("usuario@gmail.com")));
    }
}
