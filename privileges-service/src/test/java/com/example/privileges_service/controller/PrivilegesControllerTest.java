package com.example.privileges_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.privileges_service.config.SecurityConfig;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.model.Rol;
import com.example.privileges_service.service.EstadoService;
import com.example.privileges_service.service.ModuloService;
import com.example.privileges_service.service.PrivilegesService;
import com.example.privileges_service.service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// Carga los beans relacionados con Spring mvc: controladores, validadores(@NotNull, etc.)
@WebMvcTest(PrivilegesController.class)
@Import(SecurityConfig.class)
class PrivilegesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PrivilegesService privilegesService;

    @MockBean
    private RolService rolService;

    @MockBean
    private EstadoService estadoService;

    @MockBean
    private ModuloService moduloService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void listPrivileges_returnsOkWithList() throws Exception {
        List<Privileges> lista = List.of(new Privileges());
        when(privilegesService.allPrivileges()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/privilegios/total"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }


    @Test
    void addPrivilege_returnsCreated() throws Exception {
        Privileges priv = new Privileges();
        when(privilegesService.addPrivileges(any())).thenReturn(priv);

        mockMvc.perform(post("/api/v1/privilegios/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(priv)))
                .andExpect(status().isCreated());
    }


    @Test
    void listByRol_returnsOkWithList() throws Exception {
        List<Privileges> lista = List.of(new Privileges());
        when(privilegesService.findPrivilegesByRol(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/privilegios/rol/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }


    @Test
    void listByEstado_returnsOkWithList() throws Exception {
        List<Privileges> lista = List.of(new Privileges());
        when(privilegesService.findPrivilegeByEstado(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/privilegios/estado/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deletePrivilege_returnsNoContent() throws Exception {
        when(privilegesService.deletePrivileges(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/privilegios/delete/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void findRol_returnsOk() throws Exception {
        Rol rol = new Rol();
        when(rolService.validarRol(1L)).thenReturn(rol);

        mockMvc.perform(get("/api/v1/privilegios/findRol/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addRol_returnsCreated() throws Exception {
        Rol rol = new Rol();
        when(rolService.AgregarRol(any())).thenReturn(rol);

        mockMvc.perform(post("/api/v1/privilegios/addRol")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rol)))
            .andExpect(status().isCreated());
    }

    @Test
    void updateRol_returnsOk() throws Exception {
        Rol rol = new Rol();
        rol.setNombre("Admin");
        rol.setPrivilegios(List.of());

        when(rolService.validarRol(1L)).thenReturn(rol);
        when(rolService.AgregarRol(any())).thenReturn(rol);

        mockMvc.perform(put("/api/v1/privilegios/updateRoll/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rol)))
            .andExpect(status().isOk());
    }

    @Test
    void ToggleActivo_ReturnsOK() throws Exception {
        // Arrange
        Long id = 1L;
        Privileges priv = new Privileges();
        priv.setIdPrivilege(id);
        priv.setActivo(false); 

        when(privilegesService.findPrivById(id)).thenReturn(priv);
        when(privilegesService.addPrivileges(any(Privileges.class))).thenReturn(priv);

        
        mockMvc.perform(patch("/api/v1/privilegios/toggleActivo/{id}?estado=true", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.activo").value(true));

        verify(privilegesService).findPrivById(id);
        verify(privilegesService).addPrivileges(any(Privileges.class));
    }
}
