package com.example.resena_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.resena_service.model.Resenia;
import com.example.resena_service.repository.ReseniaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private RestTemplate cliente;


    public List<Resenia> listar(){
        return reseniaRepository.findAll();
    }

    public Resenia buscarId(Long id){
        return reseniaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    public Boolean validarServicio (Resenia resenia){
        String url = "http://localhost:8085/api-v1/service/exists/{id}";
        try {
            Map objeto = cliente.getForObject(url, Map.class, resenia.getIdServicio());

            if (objeto == null || objeto.isEmpty()) {
                throw new RuntimeException("El usuario no existe");
            }
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El usuario no existe");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el cliente: " + e.getMessage());
        }
    }
    public Boolean validarUsuario(Resenia resenia){
        String url = "http://localhost:8082/api-v1/register/exists/{id}";
        try {
            Map objeto = cliente.getForObject(url, Map.class, resenia.getIdUsuario());

            if (objeto == null || objeto.isEmpty()) {
                throw new RuntimeException("El usuario no existe");
            }
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El usuario no existe");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el cliente: " + e.getMessage());
        }
    }

    public Resenia agregarResenia(Resenia resenia){
        if (validarServicio(resenia) == true && validarUsuario(resenia) == true) {
            return reseniaRepository.save(resenia);
        }
        return null;
    }

    public void Eliminar(Long id){
        if (reseniaRepository.existsById(id)) {
            reseniaRepository.deleteById(id);
        }
    }
}
