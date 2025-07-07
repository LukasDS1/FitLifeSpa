package com.example.usermanagment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.usermanagment.dto.UsuarioDTO;
import com.example.usermanagment.service.UsuarioService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api-v1/managment")
@RequiredArgsConstructor

public class UsuarioController {
    
    private final UsuarioService usuarioService;

    // Controlador para listar todos los usuarios
    @Operation( summary = "Este endpoint permite obtener una lista de todos los usuarios registrados" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: Genera la lista con todos los usuarios",
    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que no hay ningun usuario registrado",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
    List<UsuarioDTO> usuarios = usuarioService.listAllUsers();
    if (usuarios.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ningún usuario registrado");
    }

    List<EntityModel<UsuarioDTO>> usuariosModel = usuarios.stream().map(usuario -> {
        EntityModel<UsuarioDTO> model = EntityModel.of(usuario);
        model.add(linkTo(methodOn(UsuarioController.class).findById(usuario.getIdUsuario())).withSelfRel());
        model.add(linkTo(methodOn(UsuarioController.class).deleteUser(usuario.getIdUsuario())).withRel("eliminar"));
        model.add(linkTo(methodOn(UsuarioController.class).updateUser(usuario.getIdUsuario(), usuario)).withRel("actualizar"));
        return model;
    }).toList();

    CollectionModel<EntityModel<UsuarioDTO>> collectionModel = CollectionModel.of(
        usuariosModel,
        linkTo(methodOn(UsuarioController.class).getAll()).withSelfRel()
    );

    return ResponseEntity.ok(collectionModel);
}

    //Controlador para listar por id todos los usuarios
    @Operation( summary = "Este endpoint permite obtener los usuarios registrados por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: muestra al usuario al que se llama por su ID",
    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que no se ha encontrado el usuario",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @GetMapping("/listid/{idUsuario}")
    public ResponseEntity<?> findById(@PathVariable Long idUsuario) {
    UsuarioDTO usuarioDTO = usuarioService.findByID(idUsuario);
    if (usuarioDTO == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    EntityModel<UsuarioDTO> model = EntityModel.of(usuarioDTO);
    model.add(linkTo(methodOn(UsuarioController.class).findById(idUsuario)).withSelfRel());
    model.add(linkTo(methodOn(UsuarioController.class).getAll()).withRel("todos"));
    model.add(linkTo(methodOn(UsuarioController.class).deleteUser(idUsuario)).withRel("eliminar"));
    model.add(linkTo(methodOn(UsuarioController.class).updateUser(idUsuario, usuarioDTO)).withRel("actualizar"));

    return ResponseEntity.ok(model);
}

    @Operation(summary = "Este endpoint permite actualizar la información de los usuarios registrados por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK: Actualiza al usuario por su id y devuelve su información actualizada",
    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
    @ApiResponse(responseCode = "404", description = "NOT FOUND: Genera un texto el cual indica que no se ha encontrado el usuario",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "409", description = "CONFLICT: El email ya está registrado por otro usuario",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "400", description = "BAD REQUEST: Error en los datos enviados o petición incorrecta",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR: Error inesperado en el servidor",
    content = @Content(schema = @Schema(implementation = String.class)))
})
    @PatchMapping("/update/{idUsuario}")
    public ResponseEntity<?> updateUser(@PathVariable Long idUsuario, @RequestBody UsuarioDTO usuarioDTO) {
    try {
        UsuarioDTO usuario = usuarioService.updateUser(idUsuario, usuarioDTO);
        EntityModel<UsuarioDTO> model = EntityModel.of(usuario);
        model.add(linkTo(methodOn(UsuarioController.class).findById(idUsuario)).withRel("ver"));
        model.add(linkTo(methodOn(UsuarioController.class).getAll()).withRel("todos"));
        model.add(linkTo(methodOn(UsuarioController.class).deleteUser(idUsuario)).withRel("eliminar"));

        return ResponseEntity.ok(model);
    } catch (RuntimeException e) {
        String mensaje = e.getMessage();
        if (mensaje.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + mensaje);
        } else if (mensaje.contains("email")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + mensaje);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + mensaje);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
    }
}


    @Operation( summary = "Este endpoint permiter eliminar a los usuarios registrados por su ID" )
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "OK: borra al usuario por su id y genera un texto el cual indica que ha sido exitosa la operacion",
    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "404",description = "NOT FOUND: Genera un texto el cual indica que no se ha encontrado el usuario",
    content = @Content(schema = @Schema(implementation = String.class)))
    }
    )
    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<?> deleteUser(@PathVariable Long idUsuario) {
    try {
        usuarioService.deleteUser(idUsuario);
        EntityModel<String> response = EntityModel.of("El usuario ha sido borrado con éxito");
        response.add(linkTo(methodOn(UsuarioController.class).getAll()).withRel("usuarios"));
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al encontrar el usuario");
    }
}
    
    

}
