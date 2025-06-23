package com.example.privileges_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.privileges_service.model.*;
import com.example.privileges_service.repository.EstadoRepository;
import com.example.privileges_service.repository.ModuloRepository;
import com.example.privileges_service.repository.PrivilegesRepository;
import com.example.privileges_service.repository.RolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivilegesService {
   
    private final PrivilegesRepository privRepository;

    
    private final RolRepository rolRepository;

    
    private final EstadoRepository estadoRepository;

    
    private final  ModuloRepository moduloRepository;

    public Privileges addPrivileges(Privileges privi){
        return privRepository.save(privi);
    }

    public Boolean deletePrivileges(Long id) {
        if (privRepository.existsById(id)) {
            privRepository.deleteById(id);
            return true;
        }
        return false;
         
    }
    public List<Privileges> findPrivilegesByRol(Long id){
        if (rolRepository.existsById(id)) {
            Rol rol = rolRepository.findById(id).orElseThrow();
            return privRepository.findByRol(rol);
        }
        return null;
    }
    public List<Privileges> findPrivilegeByEstado(Long id){
        if (estadoRepository.existsById(id)) {
            Estado estado = estadoRepository.findById(id).orElseThrow();
            return privRepository.findByEstado(estado);
        }
        return null;
    }
    public List<Privileges> findPrivilegesByModulo(Long id){
        if (moduloRepository.existsById(id)) {
            Modulo modulo = moduloRepository.findById(id).orElseThrow();
            return privRepository.findByModulo(modulo);
        }
        return null;
    }

    public List<Privileges> allPrivileges(){
        return privRepository.findAll();
    }

    public Privileges findPrivById(Long id){
        return privRepository.findById(id).orElseThrow();
    }

    
}
