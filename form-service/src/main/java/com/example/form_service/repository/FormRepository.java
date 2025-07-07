package com.example.form_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.form_service.model.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Long>{

} 
