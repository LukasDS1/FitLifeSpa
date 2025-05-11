package com.example.payment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment_service.model.Pago;
import com.example.payment_service.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoServices {
    @Autowired
    private PagoRepository pagoRepository;

    public Pago agregarPago(Pago pag){
        return pagoRepository.save(pag);
    }

    public List<Pago> ListarPagos(){
        return pagoRepository.findAll();
    }

    public List<Pago> buscarPagosUsuario(Long id){
        return pagoRepository.findByIdUsuario(id);
    }

    public Pago buscarPagoId(Integer id){
        return pagoRepository.findById(id).orElse(null);
    }

    public Boolean borrarPago(int id){
        if (pagoRepository.existsById(id)){
            pagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Pago actualizarPago(Pago pag){
        return pagoRepository.save(pag);
    }

    public List<Pago> listarPorEstado(Long id){
        return pagoRepository.findByIdEstado(id);
    }

    public List<Pago> ListarPorPlan(Long id){
        return pagoRepository.findByIdPlan(id);
    }
    
}