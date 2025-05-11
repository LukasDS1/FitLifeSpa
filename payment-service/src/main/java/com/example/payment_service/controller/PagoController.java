package com.example.payment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment_service.model.Pago;
import com.example.payment_service.service.PagoServices;

@RestController
@RequestMapping("api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoServices pagoServices;

    @PostMapping
    public ResponseEntity<Pago> crearPago (@RequestBody Pago pag){
        pagoServices.agregarPago(pag);
        return ResponseEntity.status(HttpStatus.CREATED).body(pag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPagoId(@PathVariable Long id){
        try {
            Pago pago = pagoServices.buscarPagoId(id);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Pago>> buscarPagosUsuario (@PathVariable Long id){
        List<Pago> pagos = pagoServices.buscarPagosUsuario(id);
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/total")
    public ResponseEntity<List<Pago>> buscarTodo(){
        List<Pago> pagos = pagoServices.ListarPagos();
        if (pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pag){
        try{
            Pago pago1 = new Pago();
            pago1.setIdPago(id);
            pago1.setIva(pag.getIva());
            pago1.setNeto(pag.getNeto());
            pago1.setTotal(pag.getTotal());
            pago1.setFechaPago(pag.getFechaPago());
            pago1.setIdUsuario(pag.getIdUsuario());
            pagoServices.actualizarPago(pago1);
            return ResponseEntity.ok(pago1);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPago (@PathVariable Long id) {
        Boolean validar = pagoServices.borrarPago(id);
        if (validar == false){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/planes/{id}")
    public ResponseEntity<List<Pago>> pagosPlanes (@PathVariable Long id){
        List<Pago> pagos = pagoServices.ListarPorPlan(id);
        if(pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/estado/{id}")
    public ResponseEntity<List<Pago>> estadoPago (@PathVariable Long id){
        List<Pago> pagos = pagoServices.listarPorEstado(id);
        if (pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }
}
