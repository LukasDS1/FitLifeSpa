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


@RestController
@RequestMapping("/api-v1")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping("/form")
    public ResponseEntity<?> saveForm(@RequestBody Form form) {
        try {
            if (formService.saveForm(form)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("formulario creado:\n" + form);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el formulario");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido una excepción al crear el formulario:" + e.getMessage());
        }
    }
    
}
