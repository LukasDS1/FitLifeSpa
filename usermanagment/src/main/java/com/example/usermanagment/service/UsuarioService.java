package com.example.usermanagment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.usermanagment.dto.UsuarioDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UsuarioService {

    private final RestTemplate restTemplate;
    private final String URL = "http://localhost:8082/api-v1/register";

    //Listar todos los usuarios
    public List<UsuarioDTO> listAllUsers(){
        try {
            UsuarioDTO[] usuarios = restTemplate.getForObject(URL + "/getall", UsuarioDTO[].class);
            return Arrays.asList(usuarios);
            
        } catch (HttpClientErrorException.NotFound e) {
            e.getResponseBodyAsString();
            return new ArrayList<>();
        }
    }

    //Buscar por ID
    public UsuarioDTO findByID(Long idUsuario){
        try {
            return restTemplate.getForObject(URL + "/exists/{idUsuario}", UsuarioDTO.class,idUsuario);
        } catch (Exception e) {
            return null;
        }

    }

    // Actualizar usuario
public UsuarioDTO updateUser(Long idUsuario, UsuarioDTO usuario) {
    try {
        return restTemplate.patchForObject(URL + "/actualizar/{idUsuario}", usuario, UsuarioDTO.class, idUsuario);
    } catch (HttpClientErrorException e) {
        HttpStatusCode status = e.getStatusCode();
        if (status.value() == 404) {
            throw new RuntimeException("El usuario con ID " + idUsuario + " no existe.");
        } else if (status.value() == 409) {
            throw new RuntimeException("El email ya está registrado por otro usuario.");
        } else {
            throw new RuntimeException("Error inesperado: " + status.value());
        }
    } catch (RestClientException e) {
        throw new RuntimeException("Error de comunicacion: " + e.getMessage());
    }
}
    //Borrar usuario
    public void deleteUser(Long idUsuario){
         restTemplate.delete(URL+"/delete/{idUsuario}",idUsuario);
    }




}
