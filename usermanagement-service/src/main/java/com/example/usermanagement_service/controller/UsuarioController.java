package com.example.usermanagement_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.usermanagement_service.model.Usuario;
import com.example.usermanagement_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api-v1/modificar")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/actualizar")
    public ResponseEntity<Usuario> UserUpdate(@RequestBody Usuario usuario) {
        try {
            if(usuarioService.Exist(usuario.getEmail())){
            usuarioService.saveUser(usuario);
            return ResponseEntity.ok().body(usuario);
            
        }
         return ResponseEntity.badRequest().build();


        } catch (Exception e) {
          throw new RuntimeException("¡Error al encontrar el usuario!") ;
        }
    }


    @PostMapping("/eliminar")
    public ResponseEntity <String> UserDelete(@RequestBody Usuario usuario) {
        try {
            if(usuarioService.Exist(usuario.getEmail())){
            usuarioService.deleteUser(usuario.getEmail());
            return ResponseEntity.ok().body("Usuario eliminado correctamente");
            }else{
            return ResponseEntity.badRequest().body("Usuario inexistente o Datos puestos de manera incorrecta");
            }
        } catch (Exception e) {
            throw new RuntimeException("!Error al encontrar al usuario¡");
        }
    }
    
}
