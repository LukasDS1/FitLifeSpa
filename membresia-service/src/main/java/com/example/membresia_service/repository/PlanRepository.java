package com.example.membresia_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.membresia_service.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long>{


}
