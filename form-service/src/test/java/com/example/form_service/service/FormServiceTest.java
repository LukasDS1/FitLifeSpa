package com.example.form_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.form_service.model.Form;
import com.example.form_service.repository.FormRepository;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest {

    @Mock
    private FormRepository formRepository;

    @InjectMocks
    private FormService formService;

    private Form sampleForm;

    @BeforeEach
    void setUp() {
        sampleForm = new Form(
            1L,
            "Juan",
            "Pérez",
            "juan@ejemplo.com",
            "Consulta",
            "Mensaje ejemplo");
    }

    @Test
    void saveForm_returnsFormWhenFormIsValid() {
        when(formRepository.save(sampleForm)).thenReturn(sampleForm);

        boolean result = formService.saveForm(sampleForm);

        assertTrue(result);
        verify(formRepository).save(sampleForm);
    }

    @Test
    void getForm_returnsListOfForms() {
        List<Form> lista = Arrays.asList(sampleForm);
        when(formRepository.findAll()).thenReturn(lista);

        List<Form> result = formService.getForm();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(formRepository).findAll();
    }

    @Test
    void getFormId_returnsFormWhenIdExists() {
        when(formRepository.existsById(1L)).thenReturn(true);
        when(formRepository.findById(1L)).thenReturn(Optional.of(sampleForm));

        Form result = formService.getFormId(1L);

        assertNotNull(result);
        assertEquals(sampleForm.getIdFormulario(), result.getIdFormulario());
        verify(formRepository).findById(1L);
    }




}
