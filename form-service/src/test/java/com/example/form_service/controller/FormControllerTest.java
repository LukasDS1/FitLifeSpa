package com.example.form_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.form_service.model.Form;
import com.example.form_service.service.FormService;

@WebMvcTest(FormController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FormControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FormService formService;

    private Form sampleForm;

    @BeforeEach
    void setUp() {
        sampleForm = new Form(
            1L,
            "Juan",
            "Pérez",
            "juan@gmail.com",
            "Consulta",
            "Mensaje de ejemplo"
        );
    }

    @Test
    void saveForm_returns201WhenValid() throws Exception {
        when(formService.saveForm(any(Form.class))).thenReturn(true);

        String json = """
                {
                    "idFormulario": 1,
                    "nombre": "Juan",
                    "apellidos": "Pérez",
                    "correo": "juan@gmail.com",
                    "titulo": "Consulta",
                    "mensaje": "Mensaje ejemplo"
                }
                """;

        mockMvc.perform(post("/api-v1/form/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("formulario creado con ")));
    }

    @Test
    void getForms_returns200WithList() throws Exception {
        List<Form> forms = Arrays.asList(sampleForm);
        when(formService.getForm()).thenReturn(forms);

        mockMvc.perform(get("/api-v1/form")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void getFormById_returns200WhenFound() throws Exception {
        when(formService.getFormId(1L)).thenReturn(sampleForm);

        mockMvc.perform(get("/api-v1/form/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("juan@gmail.com"));
    }
}
