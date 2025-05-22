package com.example.membresia_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.membresia_service.model.Membresia;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia,Long>{
}
