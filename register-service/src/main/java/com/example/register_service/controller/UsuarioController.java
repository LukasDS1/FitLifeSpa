package com.example.register_service.controller;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.register_service.model.Usuario;
import com.example.register_service.service.UsuarioService;
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
    @PostMapping("/crearUsuario")
    public ResponseEntity<?> CrearUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.getByMail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
    }

    //Comprobar la existencia de un usuario por email
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
    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<?> deleteById(@PathVariable Long idUsuario) {
    try {
        usuarioService.deleteByid(idUsuario);
        return ResponseEntity.ok("El usuario ha sido borrado con éxito!");
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

    //Actualizar al usaurio por id
    @PatchMapping("/actualizar/{idUsuario}")
public ResponseEntity<?> updateUsuario (@PathVariable Long idUsuario, @RequestBody Usuario usuario){
    try {
        usuario.setIdUsuario(idUsuario);
        Usuario updatedUsuario = usuarioService.UpdateUserById(usuario);
        return ResponseEntity.ok(updatedUsuario);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
    

    //Obtener todos los usuarios de una Lista
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


