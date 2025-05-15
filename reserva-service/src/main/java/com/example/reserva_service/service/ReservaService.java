package com.example.reserva_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.model.Usuario;
import com.example.reserva_service.repository.ReservaRepository;
import com.example.reserva_service.repository.ServicioRepository;
import com.example.reserva_service.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id){
        return reservaRepository.findById(id).orElseThrow();
    }

    public Reserva agregarReserva(Reserva reserva, Long idUsuario, Long idSercivio){
        if (usuarioRepository.existsById(idUsuario) == false){
            return null;
        }
        if (servicioRepository.existsById(idSercivio)){
            return null;
        }
        return reservaRepository.save(reserva);
    }

    public void borrarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> listarPorIdUser(Usuario usuario){
        return reservaRepository.findByUsuario(usuario);
    }

}
