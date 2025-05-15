package com.example.privileges_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.privileges_service.model.Estado;
import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.model.Rol;

import java.util.List;


@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Long>{
    List<Privileges> findByRol(Rol rol);
    List<Privileges> findByModulo(Modulo modulo);
    List<Privileges> findByEstado(Estado estado);
}
