package com.example.usermanagement_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.usermanagement_service.model.Usuario;
import com.example.usermanagement_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api-v1/modificar")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/listarall")
    public ResponseEntity<List<Usuario>> FindAllUser() {
        return ResponseEntity.ok(usuarioService.findAllUser());
    }

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<Usuario> findUsuarioByID(@PathVariable Long idUsuario) {
        Optional<Usuario> exist = usuarioService.getbyId(idUsuario);
        try {
            if (exist.isPresent()) {
                return ResponseEntity.ok().body(exist.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException("Error interno ");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> UserUpdate(@RequestBody Usuario usuario) {
        try {
            if (usuarioService.validateUser(usuario.getEmail())) {
                usuarioService.saveUser(usuario);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("Usuario " + usuario.getEmail() + " actualizado con exito!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("¡Error al encontrar el usuario!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> UserDelete(@RequestBody Usuario usuario) {
        try {
            if (usuarioService.validateUser(usuario.getEmail())) {
                usuarioService.deleteUser(usuario.getEmail());
                return ResponseEntity.ok().body("Usuario eliminado correctamente");
            } else {
                return ResponseEntity.badRequest().body("Usuario inexistente o Datos puestos de manera incorrecta");
            }
        } catch (Exception e) {
            throw new RuntimeException("!Error al encontrar al usuario¡");
        }
    }

}
