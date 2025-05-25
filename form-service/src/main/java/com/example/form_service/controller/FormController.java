package com.example.form_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.form_service.model.Form;
import com.example.form_service.service.FormService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api-v1/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping("/post")
    public ResponseEntity<String> saveForm(@RequestBody Form form) {
        try {
            if (formService.saveForm(form)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("formulario creado con éxito.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el formulario");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido una excepción al crear el formulario:" + e.getMessage());
        }
    }

    @GetMapping("/get/{idForm}")
    public ResponseEntity<Form> getFormById(@PathVariable Long idForm) {
        try {
            if (idForm != null) {
                return ResponseEntity.status(HttpStatus.OK).body(formService.getForm(idForm));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar formulario por id: " + e.getStackTrace());
        }
    }
    
    
}
