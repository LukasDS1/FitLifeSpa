package com.example.soporteservice.service;



import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.repository.MotivoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MotivoService {

    private final MotivoRepository motivoRepository;

    
    public Optional<Motivo> getMotivo(Long idMotivo){
        try {
            return motivoRepository.findById(idMotivo);
        } catch (Exception e) {
          throw new RuntimeException("Hola");
        }
    }

    public Motivo createMotivo(Motivo motivo) {
        try {
            return motivoRepository.save(motivo);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Error al guardar motivo: " + e.getMessage());
        }
    }

}
