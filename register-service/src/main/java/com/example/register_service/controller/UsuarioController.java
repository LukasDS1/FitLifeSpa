package com.example.register_service.controller;


import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api-v1/register")
@RequiredArgsConstructor
public class UsuarioController {
    
    
    private final UsuarioService usuarioService;

    //Crear usuario al registrarse
    @Operation( summary = "Este endpoint permite crear Usuarios" )
    @ApiResponses(value = { @ApiResponse(responseCode = "201",description = "CREATED: Indica que el usuario ha sido creado con exito",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "400",description = "BAD REQUEST: Genera un texto el cual indica que el usuario ya existe",
    content = @Content(schema = @Schema(implementation = Usuario.class)))
    }
    )
    @PostMapping("/crearUsuario")
    public ResponseEntity<?> CrearUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.getByMail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
    }

    //Comprobar la existencia de un usuario por email
    @Operation( summary = "Este endpoint permite comprobar la existencia de un usuario por email" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el usuario ha sido encontrado con exito",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que el usuario no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = Usuario.class)))
    }
    )
    @PostMapping("/exists")
    public ResponseEntity<?> getUserByMail(@RequestBody Usuario usuario) {
        try {
            Usuario usuario1 = usuarioService.getByMail(usuario.getEmail());
            if (usuario1 != null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario1);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar." + e.getMessage());
        }
    }

    //Compobrar la existencia de un usuario por ID
    @Operation( summary = "Este endpoint permite comprobar la existencia de un usuario por ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el usuario ha sido encontrado con exito",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que el usuario no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = Usuario.class)))
    }
    )
    @GetMapping("/exists/{id}")
    public ResponseEntity<?> existsById(@PathVariable Long id) {
    try {
        Usuario usuario1 = usuarioService.findByID(id);
        return ResponseEntity.ok(usuario1);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID: " + id + " no encontrado!");
    }
}


    //Borrar al usuario por id
    @Operation( summary = "Este endpoint permite borrar un usuario por ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que el usuario ha sido borrado con exito",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que el usuario no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<?> deleteById(@PathVariable Long idUsuario) {
    try {
        usuarioService.deleteByid(idUsuario);
        return ResponseEntity.ok("El usuario ha sido borrado con exito!");
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

    //Actualizar al usaurio por id
    @Operation( summary = "Este endpoint permite actualizar una informacion de un usuario buscandolo por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que la informacion del usuario ha sido actualizada con exito",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Indica que el usuario no ha sido encontrado",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "409",description = "CONFLICT: Idica que la request no ha sido por que hay un conflicto en la informacion, en este caso que el email no puede estar duplicado",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @PatchMapping("/actualizar/{idUsuario}")
    public ResponseEntity<?> updateUsuario (@PathVariable Long idUsuario, @RequestBody Usuario usuario){
    try {
        usuario.setIdUsuario(idUsuario);
        Usuario updatedUsuario = usuarioService.UpdateUserById(usuario);
        return ResponseEntity.ok(updatedUsuario);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ingresado ya está en uso");
    } 
}
    

    //Obtener todos los usuarios de una Lista
    @Operation( summary = "Este endpoint permite obtener una lista con todos los usuarios registrados" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Indica que los usuarios han sido encontrados y los imprime en pantalla",
    content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que no hay usuarios registrados",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/getall")
    public ResponseEntity<?> getAllUsers() {
    try {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen usuario registrados");
    }
}


  

  

}


