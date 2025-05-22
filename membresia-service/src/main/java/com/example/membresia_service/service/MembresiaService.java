package com.example.membresia_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.membresia_service.model.Membresia;
import com.example.membresia_service.repository.MembresiaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MembresiaService {


    private final MembresiaRepository membresiaRepository;


    
    public List<Membresia> getByAll(){
        return membresiaRepository.findAll();
    }

    public  Optional<Membresia> findByid(Long idMembresia){
        return membresiaRepository.findById(idMembresia);

    }

    public Optional<Membresia> getMembresiaById (Long idMembresia){
        return membresiaRepository.findById(idMembresia);
    }
    
    public Membresia saveMembresia(Membresia membresia){
        return membresiaRepository.save(membresia);
    }


    public Boolean deleteMembresia(Long idMembresia) {
        Optional<Membresia> exist = membresiaRepository.findById(idMembresia);
        try {
            if (exist.isEmpty()) {
                return false;
            } else {
                membresiaRepository.deleteById(idMembresia);
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException("");
        }

    }


     /** Hay que revisar */

    public Membresia updatMembresia (Membresia membresia){
        Optional<Membresia> exist = membresiaRepository.findById(membresia.getIdMembresia());
        if(exist.isEmpty()){
            throw new RuntimeException();
        }else{
            Membresia membresia2 = exist.get();
            membresia2.getIdMembresia();
            membresia2.setNombre(membresia.getNombre());
            membresia2.setDescripcion(membresia.getDescripcion());
            membresia2.setPlan(membresia.getPlan());
             return membresiaRepository.save(membresia2);
        }

    }

    
    /**   metodo opcional
    public Boolean deletemembresia(Long idMembresia){
        try {
            if(membresiaRepository.existsById(idMembresia)){
                membresiaRepository.deleteById(idMembresia);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error Membresia con ID "+idMembresia+" No encontrado");
        }
    } */



}

