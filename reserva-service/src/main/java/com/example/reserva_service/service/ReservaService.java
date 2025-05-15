package com.example.reserva_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reserva_service.model.Reserva;
import com.example.reserva_service.model.Usuario;
import com.example.reserva_service.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    // TODO: Cambiar función de login para que devuelva el Object<Usuario>.
    //public Usuario getByIdUsuario(Long)

    public List<Reserva> findReservas() {
        return reservaRepository.findAll();
    }

    public Boolean addService(Reserva reserva) {
        if (reserva != null) {
            reservaRepository.save(reserva);
            return true;
        }
        return false;
    }

    public Boolean deleteService(Long reservaId) {
        if (reservaId != null) {
            reservaRepository.deleteById(reservaId);
            return true;
        }
        return false;
    }

    public Boolean updateService(Reserva reserva) {
        if (reserva == null) {
            return false;
        }

        Reserva reserva1 = new Reserva();
        reserva1.setIdReserva(reserva.getIdReserva());
        reserva1.setFechaReserva(reserva.getFechaReserva());
        reserva1.setFechaContrato(reserva.getFechaContrato());
        reserva1.setDescripcion(reserva.getDescripcion());
        reserva1.setIdEntrenador(reserva.getIdEntrenador());

        reservaRepository.save(reserva1);
        return true;
    }

}
