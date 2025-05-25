package com.example.privileges_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.privileges_service.model.Estado;
import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.model.Rol;
import com.example.privileges_service.service.EstadoService;
import com.example.privileges_service.service.ModuloService;
import com.example.privileges_service.service.PrivilegesService;
import com.example.privileges_service.service.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/privilegios")
@RequiredArgsConstructor

public class PrivilegesController {
  
    private final PrivilegesService privilegesService;
    private final RolService rolService;
    private final EstadoService estadoService;
    private final ModuloService moduloService;

    @GetMapping("/total")
    public ResponseEntity<List<Privileges>> listPrivileges(){
        List<Privileges> privi = privilegesService.allPrivileges();
        if (privi.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(privi);
    }

    @PostMapping("/add")
    public ResponseEntity<Privileges> addPrivilege(@RequestBody Privileges privi) {
        try {
            privilegesService.addPrivileges(privi);
            return ResponseEntity.status(HttpStatus.CREATED).body(privi);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/rol/{id}")
    public ResponseEntity<List<Privileges>> listByRol(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegesByRol(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }
    
    @GetMapping("/estado/{id}")
    public ResponseEntity<List<Privileges>> listByEstado(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegeByEstado(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }

    @GetMapping("/modulo/{id}")
    public ResponseEntity<List<Privileges>> listByModulo(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegesByModulo(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrivilege(@PathVariable Long id){
        if (privilegesService.deletePrivileges(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este objeto no existe");
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privileges> findbyIdprivilege(@PathVariable Long id) {
        try {
            Privileges priv = privilegesService.findPrivById(id);
            return ResponseEntity.ok(priv);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findRol/{id}")
    public ResponseEntity<Rol> findRol(@PathVariable Long id){
        Rol rol = rolService.validarRol(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addRol")
    public ResponseEntity<Rol> addRol(@RequestBody Rol rol){
        try {
            rolService.AgregarRol(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(rol);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findEstado/{id}")
    public ResponseEntity<Estado> findEstado(@PathVariable Long id){
        Estado estado = estadoService.validarEstado(id);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addEstado")
    public ResponseEntity<Estado> addEstado(@RequestBody Estado estado){
        try {
            estadoService.agregarEstado(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findModulo/{id}")
    public ResponseEntity<Modulo> findModulo(@PathVariable Long id){
        Modulo modulo = moduloService.validarModulo(id);
        if (modulo != null) {
            return ResponseEntity.ok(modulo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addModulo")
    public ResponseEntity<Modulo> addModulo(@RequestBody Modulo modulo){
        try {
            moduloService.agregarModulo(modulo);
            return ResponseEntity.status(HttpStatus.CREATED).body(modulo);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }
}
