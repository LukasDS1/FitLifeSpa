package com.example.soporteservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.soporteservice.model.Estado;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.EstadoRepository;
import com.example.soporteservice.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EstadoRepository estadoRepository;


    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getById(Long idTicket){
        return ticketRepository.findById(idTicket);
    }

    public Ticket createTicket(Ticket ticket){
        if(ticket.getEstado() == null){
            Estado estado = estadoRepository.findById(1L).orElseThrow(()-> new RuntimeException("Estado no encontrado"));
            ticket.setEstado(estado);
        }
        return ticketRepository.save(ticket);
    }

    public Ticket updateStateTicket(Ticket ticket){
        if(ticketRepository.existsById(ticket.getIdTicket())){
            Ticket ticket2 = ticketRepository.findById(ticket.getIdTicket()).orElseThrow(()-> new RuntimeException(""));
            ticket2.setEstado(ticket.getEstado());
            return ticketRepository.save(ticket2);
        }
        throw new RuntimeException("Ticket con ID: "+ticket.getIdTicket()+" no encontrado!");
    }

    public Ticket getTicketbyId2(Long idTicket){
        Ticket ticket = ticketRepository.findById(idTicket).orElseThrow();
        ticket.getMotivo().getIdMotivo();
        ticket.getEstado().getIdEstado();
        ticket.getUsuario().getIdUsuario();
        ticket.getHistorial().size();

        return ticket;
    }
    

    

 














}
