package com.example.privileges_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.privileges_service.model.Estado;
import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.model.Rol;
import com.example.privileges_service.service.EstadoService;
import com.example.privileges_service.service.ModuloService;
import com.example.privileges_service.service.PrivilegesService;
import com.example.privileges_service.service.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/privilegios")
@RequiredArgsConstructor
public class PrivilegesController {
  
    private final PrivilegesService privilegesService;
    private final RolService rolService;
    private final EstadoService estadoService;
    private final ModuloService moduloService;


    @Operation(summary = "Permite obtener una lista con todas los privilegios")
    @ApiResponses(value ={
        @ApiResponse(responseCode ="200", description = "Genero una lista con todas las reservas disponibles", content = @Content(schema = @Schema(implementation = Privileges.class))),
        @ApiResponse(responseCode = "204", description = "no devolvera nada ya que la lista esta vacia.")
    } )
    @GetMapping("/total")
    public ResponseEntity<List<Privileges>> listPrivileges(){
        List<Privileges> privi = privilegesService.allPrivileges();
        if (privi.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(privi);
    }

    @Operation(summary = "Agrega un nuevo privilegio")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Privilegio creado correctamente", content = @Content(schema = @Schema(implementation = Privileges.class))),
        @ApiResponse(responseCode = "400", description = "Error en el cuerpo de la solicitud")
    })
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
    
    @Operation(summary = "Lista los privilegios según el rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Privilegios encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Privileges.class)))),
        @ApiResponse(responseCode = "204", description = "No se encontraron privilegios para el rol")
    })
    @GetMapping("/rol/{id}")
    public ResponseEntity<List<Privileges>> listByRol(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegesByRol(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }
    
    @Operation(summary = "Lista los privilegios según el estado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Privilegios encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Privileges.class)))),
        @ApiResponse(responseCode = "204", description = "No se encontraron privilegios para el estado")
    })
    @GetMapping("/estado/{id}")
    public ResponseEntity<List<Privileges>> listByEstado(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegeByEstado(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }

    @Operation(summary = "Lista los privilegios según el módulo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Privilegios encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Privileges.class)))),
        @ApiResponse(responseCode = "204", description = "No se encontraron privilegios para el módulo")
    })
    @GetMapping("/modulo/{id}")
    public ResponseEntity<List<Privileges>> listByModulo(@PathVariable Long id){
        List<Privileges> priv = privilegesService.findPrivilegesByModulo(id);
        if (priv.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(priv);
    }

     @Operation(summary = "Elimina un privilegio por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Privilegio eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrivilege(@PathVariable Long id){
        if (privilegesService.deletePrivileges(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este objeto no existe");
        
    }

    @Operation(summary = "Busca un privilegio por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Privilegio encontrado", content = @Content(schema = @Schema(implementation = Privileges.class))),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
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

    @Operation(summary = "Busca un rol por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado", content = @Content(schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/findRol/{id}")
    public ResponseEntity<Rol> findRol(@PathVariable Long id){
        Rol rol = rolService.validarRol(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        }
        return ResponseEntity.notFound().build();
    }

     @Operation(summary = "Agrega un Rol nuevo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rol agregado correctamente", content = @Content(schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Hubo un error en el cuerpo")
    })
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

    @Operation(summary = "Busca un estado por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado encontrado", content = @Content(schema = @Schema(implementation = Estado.class))),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/findEstado/{id}")
    public ResponseEntity<Estado> findEstado(@PathVariable Long id){
        Estado estado = estadoService.validarEstado(id);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Agrega un estado nuevo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Estado agregado correctamente", content = @Content(schema = @Schema(implementation = Estado.class))),
        @ApiResponse(responseCode = "400", description = "Hubo error con el cuerpo")
    })
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

    @Operation(summary = "Busca un modulo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Modulo encontrado", content = @Content(schema = @Schema(implementation = Modulo.class))),
        @ApiResponse(responseCode = "404", description = "Modulo no encontrado")
    })
    @GetMapping("/findModulo/{id}")
    public ResponseEntity<Modulo> findModulo(@PathVariable Long id){
        Modulo modulo = moduloService.validarModulo(id);
        if (modulo != null) {
            return ResponseEntity.ok(modulo);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Agrega un modulo nuevo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Modulo agregado correctamente", content = @Content(schema = @Schema(implementation = Modulo.class))),
        @ApiResponse(responseCode = "400", description = "Hubo error en el cuerpo")
    })
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

    @Operation(summary = "Actualiza un rol existente por su id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente", content = @Content(schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro el rol")
    })
    @PutMapping("/updateRoll/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rolActualizado) {
        Rol rolExistente = rolService.validarRol(id);

        if (rolExistente != null) {
            // Actualiza los campos necesarios
            rolExistente.setNombre(rolActualizado.getNombre());
            rolExistente.setPrivilegios(rolActualizado.getPrivilegios());
            Rol rolGuardado = rolService.AgregarRol(rolExistente);
            return ResponseEntity.ok(rolGuardado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Activa o desactiva un privilegio")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Privilegio actualizado correctamente", content = @Content(schema = @Schema(implementation = Privileges.class))),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
    @PatchMapping("/toggleActivo/{id}")
    public ResponseEntity<Privileges> toggleActivo(@PathVariable Long id, @RequestParam boolean estado) {
        try {
            Privileges priv = privilegesService.findPrivById(id);
            priv.setActivo(estado);
            privilegesService.addPrivileges(priv); 
            return ResponseEntity.ok(priv);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
