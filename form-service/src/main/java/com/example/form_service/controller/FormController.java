package com.example.form_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.form_service.model.Form;
import com.example.form_service.service.FormService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Operation(summary = "Crear un nuevo formulario")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Formulario creado con éxito",
            content = @Content(schema = @Schema(implementation = Form.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Error al crear el formulario",
            content = @Content(schema = @Schema(type = "string", example = "Ha ocurrido una excepción al crear el formulario.")))
    })
    @PostMapping("/post")
    public ResponseEntity<String> saveForm(@RequestBody Form form) {
        try {
            if (formService.saveForm(form)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("formulario creado con éxito.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el formulario");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido una excepción al crear el formulario.");
        }
    }

    @Operation(summary = "Lista todos los formularios")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Se listan todos los formularios con éxito",
            content = @Content(schema = @Schema(implementation = Form.class)))
    })
    @GetMapping
    public ResponseEntity<List<Form>> getForms() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(formService.getForm());
        } catch (Exception e) {
            throw new RuntimeException("Error al conseguir todos los formularios.");
        }
    }
    
    @Operation(summary = "Devuelve un formulario por ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
        description = "Formulario encontrado", 
        content = @Content(schema = @Schema(implementation = Form.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Formulario no encontrado",
            content = @Content(schema = @Schema(type = "string", example = "Error al buscar formulario por ID."))
        )
    })
    @GetMapping("/{idForm}")
    public ResponseEntity<Form> getFormById(@PathVariable Long idForm) {
        try {
            if (idForm != null) {
                return ResponseEntity.status(HttpStatus.OK).body(formService.getFormId(idForm));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar formulario por ID.");
        }
    }
    
    
}
